package kr.ssok.bank.proxy.domain.transfer.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.ssok.bank.proxy.common.comm.CommunicationProtocol;
import kr.ssok.bank.proxy.common.comm.KafkaCommModule;
import kr.ssok.bank.proxy.common.comm.promise.CommQueryPromise;
import kr.ssok.bank.proxy.common.comm.promise.PromiseMessage;
import kr.ssok.bank.proxy.common.constant.FailureStatusCode;
import kr.ssok.bank.proxy.common.response.ApiResponse;
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

    @Operation(summary = "출금 이체", description = "이용기관이 등록된 한 개의 사용자 계좌로부터 대금을 출금합니다.")
    @PostMapping("/withdraw")
    public ApiResponse<String> relayWithdraw(@RequestBody TransferWithdrawRequestDTO dto) {
        try {
            CommQueryPromise promise = this.commModule.sendPromiseQuery(
                    dto.getWithdrawAccount(),
                    CommunicationProtocol.REQUEST_WITHDRAW,
                    dto, 10);

            PromiseMessage msg = promise.get();
            String result = msg.getDataObject(String.class);
            return ApiResponse.toApiResponse(result);

        } catch (Exception e) {
            log.error("Error processing Promise", e);
            return ApiResponse.of(FailureStatusCode._INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "입금 이체", description = "이용기관이 사용자의 계좌로 대금을 송금합니다.")
    @PostMapping("/deposit")
    public ApiResponse<String> relayDeposit(@RequestBody TransferDepositRequestDTO dto) {
        try {
            CommQueryPromise promise = this.commModule.sendPromiseQuery(
                    dto.getDepositAccount(),
                    CommunicationProtocol.REQUEST_DEPOSIT,
                    dto, 10);

            PromiseMessage msg = promise.get();
            String result = msg.getDataObject(String.class);
            return ApiResponse.toApiResponse(result);

        } catch (Exception e) {
            log.error("Error processing Promise", e);
            return ApiResponse.of(FailureStatusCode._INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "보상 요청", description = "트랜잭션의 보상 요청을 받습니다.")
    @PostMapping("/compensate")
    public ApiResponse<String> relayCompensate(@RequestBody CompensateRequestDTO dto) {
        try {
            CommQueryPromise promise = this.commModule.sendPromiseQuery(
                    dto.getTransactionId(),
                    CommunicationProtocol.REQUEST_COMPENSATE,
                    dto, 10);
            PromiseMessage msg = promise.get();
            String result = msg.getDataObject(String.class);
            return ApiResponse.toApiResponse(result);
        } catch (Exception e) {
            log.error("Error processing Promise", e);
            return ApiResponse.of(FailureStatusCode._INTERNAL_SERVER_ERROR, null);
        }
    }
}
