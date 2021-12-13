package metanet.springboot.restapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {
    public void validate(EventDto eventDto, Errors errors) {

        //maxPrice가 0이 아닌 경우에 basePrice가 maxPrice 보다 더 크면 에러를 발생시킴
        if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0) {
            //Field Error 발생시킴
            errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong");
            errors.rejectValue("maxPrice", "wrongValue", "MaxPrice is wrong");
            //Global Error
            errors.reject("wrongPrices", "Values for prices are wrong");
        }

        //모임등록종료일자가 모임시작날짜가 보다 더 이전 날짜이거나, 모임등록시작날짜가 모임시작날짜 보다 더 이전 인지를 체크하기
        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if(endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
                endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime()) ) {
            errors.rejectValue("endEventDateTime", "wrongValue", "EndEventDateTime is wrong");
        }

        //모임 등록시작일자가 모임등록 종료일자, 모임 시작, 모임종료일자보다 더 이전 날짜이면 오류발생시키기
        LocalDateTime beginEnrollDateTime = eventDto.getBeginEnrollmentDateTime();
        if(beginEnrollDateTime.isAfter(eventDto.getCloseEnrollmentDateTime()) ||
                beginEnrollDateTime.isAfter(eventDto.getBeginEventDateTime()) ||
                beginEnrollDateTime.isAfter(eventDto.getEndEventDateTime())) {
            errors.rejectValue("beginEnrollmentDateTime", "시작등록일자오류",
                    "시작등록일자는 시작종료일자보다 더 이전날짜이어야 합니다.");
        }

    }
}
