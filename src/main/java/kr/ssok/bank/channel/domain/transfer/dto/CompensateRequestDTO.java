package kr.ssok.bank.channel.domain.transfer.dto;

import lombok.Getter;

@Getter
public class CompensateRequestDTO {
    private String transactionId; // 오픈뱅킹 트랜잭션 ID
}
