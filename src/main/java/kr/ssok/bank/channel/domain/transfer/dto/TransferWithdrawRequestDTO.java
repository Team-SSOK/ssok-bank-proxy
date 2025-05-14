package kr.ssok.bank.channel.domain.transfer.dto;

import lombok.Getter;

@Getter
public class TransferWithdrawRequestDTO {
    private String transactionId; // 오픈뱅킹 트랜잭션 ID
    private int withdrawBankCode; // 출금 은행 코드
    private String withdrawAccount; // 출금 계좌
    private Long transferAmount; // 출금 금액
    private int currencyCode; // 통화 코드
    private String counterAccount; // 입금 계좌
    private int counterBankCode; // 입금 은행 코드
}
