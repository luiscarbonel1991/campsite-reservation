package com.reservation.campsite.exception;

import com.reservation.campsite.dto.response.ReservationDTO;
import com.reservation.campsite.util.RangeDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.time.LocalDate;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BusinessException {

    private BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public static BadRequestException missingParam(String... params) {
        ErrorCode code = ErrorCode.BAD_REQUEST_MISSING_PARAMETERS;
        return new BadRequestException(code,
                String.format("%s. [%s]", code.getMessageCode(), String.join(", ", params)));
    }

    public static BadRequestException invalidDateRange(LocalDate from, String nameFromParam, LocalDate to, String nameToParam) {
        ErrorCode code = ErrorCode.BAD_REQUEST_INVALID_DATE_RANGE;
        return new BadRequestException(code,
                String.format("%s. Start date can not be after end date. %s: %s, %s: %s", code.getMessageCode(),
                        nameFromParam, from, nameToParam, to));
    }

    public static BadRequestException invalidStayRange(LocalDate arrivalDate, LocalDate departureDate, int minStayDays, int maxStayDays) {
        ErrorCode code = ErrorCode.BAD_REQUEST_INVALID_STAY_RANGE;
        return new BadRequestException(code,
                String.format("%s. Stay range must be between %d and %d days. Arrival date: %s, Departure date: %s",
                        code.getMessageCode(), minStayDays, maxStayDays, arrivalDate, departureDate));
    }

    public static BadRequestException invalidArrivalDate(LocalDate arrivalDate, LocalDate departureDate, RangeDate<LocalDate> validRange) {
        ErrorCode code = ErrorCode.BAD_REQUEST_INVALID_DATE_RANGE;
        return new BadRequestException(code,
                String.format("%s. Range must be between %s and %s. Arrival date: %s, Departure date: %s",
                        code.getMessageCode(), validRange.getFrom(), validRange.getTo(), arrivalDate, departureDate));
    }

    public static BadRequestException invalidEmail(String email) {
        ErrorCode code = ErrorCode.BAD_REQUEST_INVALID_EMAIL;
        return new BadRequestException(code,
                String.format("%s. Email: %s", code.getMessageCode(), email));
    }

    public static BadRequestException reservationAlreadyExists(ReservationDTO reservation) {
        ErrorCode code = ErrorCode.BAD_REQUEST_RESERVATION_ALREADY_EXISTS;
        return new BadRequestException(code,
                String.format("%s with Email: %s, Arrival date: %s, Departure date: %s", code.getMessageCode(), reservation.getEmail(), reservation.getArrivalDate(), reservation.getDepartureDate()));
    }

    public static BadRequestException updateCancelledReservation(Instant cancelDate) {
        ErrorCode code = ErrorCode.BAD_REQUEST_UPDATE_CANCELLED_RESERVATION;
        return new BadRequestException(code,
                String.format("%s. Reservation was cancelled at %s", code.getMessageCode(), cancelDate));
    }

    public static BadRequestException lock() {
        ErrorCode code = ErrorCode.BAD_REQUEST_TO_HIGH_DEMAND;
        return new BadRequestException(code, String.format("%s. Please try again.", code.getMessageCode()));
    }

    public static BadRequestException alreadyCancelled(Long reservationId) {
        ErrorCode code = ErrorCode.BAD_REQUEST_ALREADY_CANCELLED;
        return new BadRequestException(code, String.format("%s. Reservation with Id: %s was already cancelled. ", code.getMessageCode(), reservationId));
    }
}
