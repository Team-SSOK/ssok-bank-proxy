package kr.ssok.bank.proxy.domain.transfer.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.ssok.bank.proxy.common.comm.CommunicationProtocol;
import kr.ssok.bank.proxy.common.comm.JsonUtil;
import kr.ssok.bank.proxy.common.comm.KafkaCommModule;
import kr.ssok.bank.proxy.common.comm.promise.CommQueryPromise;
import kr.ssok.bank.proxy.common.comm.promise.PromiseMessage;
import kr.ssok.bank.proxy.common.constant.FailureStatusCode;
import kr.ssok.bank.proxy.common.response.ApiResponse;
import kr.ssok.bank.proxy.common.response.BaseResponseDTO;
import kr.ssok.bank.proxy.domain.transfer.dto.CompensateRequestDTO;
import kr.ssok.bank.proxy.domain.transfer.dto.TransferDepositRequestDTO;
import kr.ssok.bank.proxy.domain.transfer.dto.TransferWithdrawRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/bank/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final KafkaCommModule commModule;

    @Operation(summary = "출금 이체", description = "출금 이체 요청을 카프카에 전송합니다.")
    @PostMapping("/withdraw")
    public ApiResponse<?> relayWithdraw(@RequestBody TransferWithdrawRequestDTO dto) {
        try {

            log.info("[출금 이체] 출금 이체 프로미스 요청을 시작합니다.");
            CommQueryPromise promise = this.commModule.sendPromiseQuery(
                    dto.getWithdrawAccount(),
                    CommunicationProtocol.REQUEST_WITHDRAW,
                    dto, 10);

            PromiseMessage msg = promise.get();
            log.info("[출금 이체] 출금 이체 프로미스 요청을 성공적으로 받았습니다.");
            ApiResponse<BaseResponseDTO> res = msg.getDataObject(ApiResponse.class);

            log.info("성공여부 : {}",res.getIsSuccess());
            log.info("메세지 : {}",res.getMessage());

            return msg.getDataObject(ApiResponse.class);

        } catch (Exception e) {
            log.error("Error processing Promise", e);
            return ApiResponse.of(FailureStatusCode._INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "입금 이체", description = "입금 이체 요청을 카프카에 전송합니다.")
    @PostMapping("/deposit")
    public ApiResponse<?> relayDeposit(@RequestBody TransferDepositRequestDTO dto) {
        try {
            log.info("[입금 이체] 입금 이체 프로미스 요청을 시작합니다.");
            CommQueryPromise promise = this.commModule.sendPromiseQuery(
                    dto.getDepositAccount(),
                    CommunicationProtocol.REQUEST_DEPOSIT,
                    dto, 10);

            PromiseMessage msg = promise.get();
            log.info("[입금 이체] 입금 이체 프로미스 요청을 성공적으로 받았습니다.");
            return msg.getDataObject(ApiResponse.class);

        } catch (Exception e) {
            log.error("Error processing Promise", e);
            return ApiResponse.of(FailureStatusCode._INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Operation(summary = "보상 요청", description = "트랜잭션의 보상 요청을 카프카에 전송합니다.")
    @PostMapping("/compensate")
    public ApiResponse<?> relayCompensate(@RequestBody CompensateRequestDTO dto) {
        try {
            CommQueryPromise promise = this.commModule.sendPromiseQuery(
                    dto.getTransactionId(),
                    CommunicationProtocol.REQUEST_COMPENSATE,
                    dto, 10);

            PromiseMessage msg = promise.get();
            return msg.getDataObject(ApiResponse.class);

        } catch (Exception e) {
            log.error("Error processing Promise", e);
            return ApiResponse.of(FailureStatusCode._INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public static void main(String[] args) {

        String jsonStr = "{\"isSuccess\":true,\"code\":\"200\",\"message\":\"요청이 성공적으로 처리되었습니다.\",\"result\":\"작업이 완료되었습니다.\"}";
        ApiResponse<?> res = JsonUtil.fromJson(jsonStr, ApiResponse.class);

        System.out.println(res.getIsSuccess());
        System.out.println(res.getMessage());
        System.out.println(res.getResult());

    }

}
