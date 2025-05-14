package kr.ssok.bank.proxy.domain.transfer.dto;

import lombok.Getter;

@Getter
public class TransferDepositRequestDTO {
    private String transactionId; // 오픈뱅킹 트랜잭션 ID
    private int depositBankCode; // 입금 은행 코드
    private String depositAccount; // 입금 계좌
    private Long transferAmount; // 입금 금액
    private int currencyCode; // 통화 코드
    private String counterAccount; // 출금 계좌
    private int counterBankCode; // 출금 은행 코드
}
