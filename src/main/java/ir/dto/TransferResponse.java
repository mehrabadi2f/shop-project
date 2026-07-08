package ir.dto;


public class TransferResponse {

    private Long transferId;
    private String status;

    public TransferResponse(Long transferId, String status) {
        this.transferId = transferId;
        this.status = status;
    }

    public Long getTransferId() { return transferId; }

    public String getStatus() { return status; }
}
