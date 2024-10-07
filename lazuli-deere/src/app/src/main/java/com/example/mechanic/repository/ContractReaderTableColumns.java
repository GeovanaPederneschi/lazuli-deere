package com.example.mechanic.repository;

public final class ContractReaderTableColumns {

    private ContractReaderTableColumns() {
    }

    public static class TableEmployee{
        public static final String TABLE_NAME = "tb_funcionario";
        public static final String _ID = "id";
        public static final String COLUMN_CPF = "CPF";
        public static final String COLUMN_NAME = "nome_func";
        public static final String COLUMN_TYPE = "tipo_funcionario";
        public static final String COLUMN_REGISTER_DATETIME = "data_admissao";
        public static final String COLUMN_PASSWORD = "senha_func";
        public static final String COLUMN_EMAIL = "email_func";
        public static final String COLUMN_PHONE = "telefone";
        public static final String BIRTHDAY_DATE = "data_nascimento";
        public static final String FILIAL_ID = "filial_id";
        public static final String ADDRESS = "endereco";
        public static final String CARGO_ID = "cargo_id";
    }

    public static class ViewReceiptPointPart{
        public static final String ID_RECEIPT = "id_tb_demanda";
        public static final String ID_CAR = "idCarrinho";
        public static final String STATUS_PROGRESS = "status_progresso";
        public static final String STATUS_DIRECTION = "status_direction";
        public static final String DATETIME_REGISTER = "datatime_cadastro";
        public static final String DATETIME_DONE = "datatime_finalizado";
        public static final String ID_RECEIPT_PART = "idtb_r_demanda_pecas";
        public static final String ID_PART = "id_tb_peca";
        public static final String COUNT_PART = "quantidade";
        public static final String ID_COLLECT_POINT = "idPontoColeta";
        public static final String TYPE_COLLECT_POINT = "tipo_ponto_coleta";
        public static final String X_LOCATION_COLLECT_POINT = "x_location_coleta";
        public static final String Y_LOCATION_COLLECT_POINT = "y_location_coleta";
        public static final String ID_STOCK_POINT = "idPontoEntrega";
        public static final String TYPE_STOCK_POINT = "tipo_ponto_entrega";
        public static final String X_LOCATION_STOCK_POINT = "x_location_entrega";
        public static final String Y_LOCATION_STOCK_POINT = "y_location_entrega";

    }

    public static class ViewEmployeeCar{
        public static final String ID_EMPLOYEE = "id_funcionario";
        public static final String COLUMN_CPF = "CPF";
        public static final String COLUMN_NAME = "nome_func";
        public static final String COLUMN_TYPE = "tipo_funcionario";
        public static final String COLUMN_REGISTER_DATETIME = "data_admissao";
        public static final String COLUMN_PASSWORD = "senha_func";
        public static final String COLUMN_EMAIL = "email_func";
        public static final String COLUMN_PHONE = "telefone";
        public static final String BIRTHDAY_DATE = "data_nascimento";
        public static final String FILIAL_ID = "filial_id";
        public static final String ADDRESS = "endereco";
        public static final String CARGO_ID = "cargo_id";
        public static final String ID_CAR = "id_carrinho";
        public static final String IDENTIFICATION_NUMBER_CAR = "num_ident";
    }
}
