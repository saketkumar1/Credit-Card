package com.creditcard.offer.controller;
import com.creditcard.offer.dto.OfferDto;
import com.creditcard.offer.entity.Account;
import com.creditcard.offer.entity.LimitOffer;
import com.creditcard.offer.response.ApiResponse;
import com.creditcard.offer.service.ILimitOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offer")
public class LimitOfferController {

    @Autowired
    ILimitOfferService limitOfferService;

    @PostMapping("/create-offer")
    public ApiResponse<LimitOffer>  createLimitOffer(@RequestBody OfferDto limitOffer) throws Exception{
        try {
            LimitOffer result =  limitOfferService.createLimitOffer(limitOffer);
            return new ApiResponse<>("Successfully added data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("/findLimitById/{limitId}")
    public ApiResponse<Optional<LimitOffer>> findLimitById(@PathVariable long limitId){
        try {
            Optional<LimitOffer> result= limitOfferService.findLimitById(limitId);
            ApiResponse<Optional<LimitOffer>> response = new ApiResponse<>("Successfully get data!", HttpStatus.OK, result);
            if(result == null){
                response.setMessage("No data found");
            }
            return response;
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping("/{limitOfferId}/updateStatus/{status}")
    public ApiResponse<LimitOffer> updateStatus( @PathVariable long limitOfferId, @RequestParam int status){
        try {
            LimitOffer result =  limitOfferService.updateStatus(limitOfferId,status);
            return new ApiResponse<>("Successfully update data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("/findActiveLimitOffers/{accountId}/date")
    public ApiResponse<List<LimitOffer>> findActiveLimitOffers(@PathVariable long accountId,@RequestParam("localDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate activeDate){
        try {
            List<LimitOffer> result =  limitOfferService.findActiveLimitOffers(accountId,activeDate);
            return new ApiResponse<>("Successfully retrieved active limit offers!", HttpStatus.OK, result);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("/findAll")
    public ApiResponse<List<LimitOffer>> findAllOffer(){
        try {
            List<LimitOffer> result =  limitOfferService.findAllOffer();
            return new ApiResponse<>("Successfully retrieved all limit offers!", HttpStatus.OK, result);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping("/{limitOfferId}/updateLimitOfferStatus/{status}")
    public ApiResponse<Account> updateLimitOfferStatus(@PathVariable long limitOfferId,@PathVariable int status) {

        try {
            Account result =  limitOfferService.updateLimitOfferStatus(limitOfferId,status);
            return new ApiResponse<>("Successfully update  limit offers!", HttpStatus.OK, result);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
