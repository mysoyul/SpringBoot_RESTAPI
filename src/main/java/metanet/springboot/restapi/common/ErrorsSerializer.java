package metanet.springboot.restapi.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

//Custom Serializer 클래스
@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {
    @Override
    public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        //직렬화 시작
        gen.writeStartArray();

        /*
        {
        "field": "limitOfEnrollment",
        "objectName": "eventDto",
        "code": "Min",
        "defaultMessage": "모임의 최소인원은 5명 이상이어야 합니다.",
        "rejectedValue": "4"
        }
         */
        //필드에러 정보 출력
        errors.getFieldErrors().forEach(e -> {
            try {
                gen.writeStartObject();
                //필드명
                gen.writeStringField("field", e.getField());
                //DTO객체명
                gen.writeStringField("objectName", e.getObjectName());
                //검증에 사용된 어노테이션
                gen.writeStringField("code", e.getCode());
                //에러메시지
                gen.writeStringField("defaultMessage", e.getDefaultMessage());
                
                Object rejectedValue = e.getRejectedValue();
                if (rejectedValue != null) {
                    //잘못입력된 입력값
                    gen.writeStringField("rejectedValue", rejectedValue.toString());
                }
                gen.writeEndObject();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        /*
        {
            "objectName": "eventDto",
            "code": "wrongPrices",
            "defaultMessage": "Values for prices are wrong"
        }
         */
        //글로벌에러 정보 출력
        errors.getGlobalErrors().forEach(e -> {
            try {
                gen.writeStartObject();
                gen.writeStringField("objectName", e.getObjectName());
                gen.writeStringField("code", e.getCode());
                gen.writeStringField("defaultMessage", e.getDefaultMessage());
                gen.writeEndObject();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        //직렬화 종료
        gen.writeEndArray();

    }

}
