package com.example.mechanic.repository;

public final class ContractReaderTableColumns {

    private ContractReaderTableColumns() {
    }

    public static class TableEmployee{
        public static final String TABLE_NAME = "tb_funcionario";
        public static final String COLUMN_CPF = "cpf_funcionario";
        public static final String COLUMN_NAME = "nome_funcionario";
        public static final String COLUMN_TYPE = "tipo_funcionario";
        public static final String COLUMN_REGISTER_DATETIME = "cadastro_datetime";
        public static final String COLUMN_PASSWORD = "senha_funcionario";
        public static final String COLUMN_EMAIL = "email_funcionario";
        public static final String COLUMN_PHONE = "numero_funcionario";
        public static final String BIRTHDAY_DATE = "aniversario_funcionario";
    }


}
