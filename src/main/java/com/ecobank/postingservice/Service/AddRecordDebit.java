package com.ecobank.postingservice.Service;

import com.ecobank.postingservice.Dao.AddParam;
import com.ecobank.postingservice.Dao.Resp;
import com.ecobank.postingservice.Entity.Utilities;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddRecordDebit {
    public Resp AddTransactionDebit(Connection conn, AddParam addParam, String batchId) {
//        Connection conn = null;
        ResultSet Result = null;
        OracleCallableStatement stmt = null;


        Integer resultout = null;

        String Output2 = null;
        String Output3 = null;
        String genbatch = null;
        Resp response = new Resp();

        List<Resp> Responses = new ArrayList<>();

        System.out.println("addParam: " + addParam.toString());
//        Batchid =response.getBatchno();

        System.out.println("Batchid : "+batchId);

        try {
//            conn = Utilities.getConnection();
            conn = conn.unwrap(OracleConnection.class);
            stmt = (OracleCallableStatement) conn.prepareCall("{?=call esb_fcc_instr_add_record(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            int cnt = 0;

            stmt.registerOutParameter(++cnt, OracleTypes.INTEGER);
            stmt.setString(++cnt, addParam.getPlatformid());
            stmt.setString(++cnt, addParam.getBranch());
            stmt.setString(++cnt, batchId);
//            stmt.setString(++cnt, genbatch);
            stmt.setString(++cnt, addParam.getReference());
            stmt.setString(++cnt, addParam.getDebitField().getAccountnum());
            stmt.setString(++cnt, addParam.getDebitField().getAccountbranch());
            stmt.setString(++cnt, addParam.getDebitField().getCrdrflag());
            stmt.setInt(++cnt, addParam.getDebitField().getTxnamount());
            stmt.setString(++cnt, addParam.getDebitField().getAmttype());
            stmt.setString(++cnt, addParam.getDebitField().getTxncurrency());
            stmt.setDate(++cnt, addParam.getInitdate());
            stmt.setDate(++cnt, addParam.getValuedate());
            stmt.setInt(++cnt, addParam.getDebitField().getSeqno());
            stmt.setString(++cnt, addParam.getDebitField().getTxndesc());
            stmt.setInt(++cnt, addParam.getDebitField().getExrate());
            stmt.setString(++cnt, addParam.getDebitField().getTrantype());
            stmt.setString(++cnt, addParam.getDebitField().getInstrumentno());
            stmt.registerOutParameter(++cnt, OracleTypes.VARCHAR);
            stmt.registerOutParameter(++cnt, OracleTypes.VARCHAR);


            stmt.execute();

            resultout = (Integer) stmt.getObject(1);

            System.out.println("resultout: " + resultout);


            Output2 = (String) stmt.getObject(19);
            Output3 = (String) stmt.getObject(20);

            response.setResponsecode(Output2);
            response.setResponseMessage(Output3);
        } catch (Exception e) {
            e.printStackTrace();
                }
//        finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//                if (Result != null) {
//                    Result.close();
//                }
//                if (stmt != null) {
//                    stmt.close();
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return response;


    }
}