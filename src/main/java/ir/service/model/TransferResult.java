package ir.service.model;


public class TransferResult {

    private Long transferId;
    private TransferStatus status;

    public TransferResult(Long transferId, TransferStatus status) {
        this.transferId = transferId;
        this.status = status;
    }

    public Long getTransferId() {
        return transferId;
    }

    public TransferStatus getStatus() {
        return status;
    }
}

