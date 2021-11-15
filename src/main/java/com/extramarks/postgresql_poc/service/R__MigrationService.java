package com.extramarks.postgresql_poc.service;

import java.util.*;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
@Configuration
public class R__MigrationService extends BaseJavaMigration {

    @Autowired
    TargetIdService targetIdService;

    @Autowired
    FetchRowService fetchRowService;

    @Value("${table.target}")
    String target;

    @Value("${table.targetForeign}")
    String targetForeign;

    @Value("${table.source}")
    String source;

    @Value("${table.sourceForeign}")
    String sourceForeign;

    @Value("${table.user_id}")
    String user_id;

    @Value("${table.id}")
    String id;

    @Value("${table.userMapping}")
    String userMapping;

    @Value("${table.serialNo}")
    String serial_no;

    @Value("${mysql.joinQuery}")
    String joinQuery;

    @Override
    public void migrate(Context context) throws Exception {
        final long migrationStartTime = System.currentTimeMillis();

        Statement insertMappingStmt = context.getConnection().createStatement();
        Statement insertUserStmt = context.getConnection().createStatement();
        Statement insertUODStmt = context.getConnection().createStatement();

        int targetLastId = targetIdService.getLastId(target,user_id);
        int targetForeignLastId = targetIdService.getLastId(targetForeign,id);
        targetForeignLastId++;

        final long connectionTime = System.currentTimeMillis();

        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet joinTableRows = factory.createCachedRowSet();

        String mysqlUrl = "jdbc:mysql://localhost/Source";
        joinTableRows.setUrl(mysqlUrl);
        joinTableRows.setUsername("root");
        joinTableRows.setPassword("utsav");
        joinTableRows.setCommand(joinQuery);
        joinTableRows.execute();

        final long connectionLatency = System.currentTimeMillis() - connectionTime;
        System.out.println("$$$$$ Join Query Execution Time $$$$$: "+connectionLatency);

        int userMappingLast = targetIdService.getLastId(userMapping,serial_no);
        userMappingLast++;

        final long mapFillStartTime =System.currentTimeMillis();

        ResultSet source_uod = fetchRowService.getResultSet(context,sourceForeign);
        Map<Integer,Integer> source_uod_map = new HashMap<>();
        while (source_uod.next()){
            source_uod_map.put(source_uod.getInt(2), source_uod_map.getOrDefault(source_uod.getInt(2),0)+1);
        }

        ResultSet target_uod = fetchRowService.getResultSet(context,targetForeign);
        Map<Integer,Integer> target_uod_map = new HashMap<>();
        while (target_uod.next()){
            target_uod_map.put(target_uod.getInt(2), target_uod_map.getOrDefault(target_uod.getInt(2),0)+1);
        }

        ResultSet userMappingResultSet = fetchRowService.getResultSet(context,userMapping);
        Map<Integer,Integer> userMap = new HashMap<>();
        while(userMappingResultSet.next()){
            userMap.put(userMappingResultSet.getInt(2),userMappingResultSet.getInt(3));
        }

        final long mapFillLatency = System.currentTimeMillis() - mapFillStartTime ;
        System.out.println("$$$$$ Previous  User Mapping Fetch Time $$$$$: "+mapFillLatency);

        while (joinTableRows.next()) {

            int user_id = joinTableRows.getInt(1);
            int user_id_bcpkup= joinTableRows.getInt(2);
            String username= joinTableRows.getString(3);
            String email= joinTableRows.getString(4);;
            String display_name= joinTableRows.getString(5);
            String password= joinTableRows.getString(6);
            String pin= joinTableRows.getString(7);
            String state_id= joinTableRows.getString(8);
            String gender= joinTableRows.getString(9);
            String postalcode= joinTableRows.getString(10);
            int	user_type_id= joinTableRows.getInt(11);
            String mobile= joinTableRows.getString(12);
            String phone= joinTableRows.getString(13);
            String city= joinTableRows.getString(14);
            String country_id= joinTableRows.getString(15);
            String board_id= joinTableRows.getString(16);
            String class_id= joinTableRows.getString(17);
            String user_photo= joinTableRows.getString(18);
            String dob= joinTableRows.getString(19);
            String school_id= joinTableRows.getString(20);
            String school_name= joinTableRows.getString(21).replace("'","''");
            String product_type= joinTableRows.getString(22);
            String parent_id= joinTableRows.getString(23);
            String age= joinTableRows.getString(24);
            String address= joinTableRows.getString(25).replace("'","''");
            String ip= joinTableRows.getString(26);
            String subscribe_me= joinTableRows.getString(27);
            String allowed_email_category= joinTableRows.getString(28);
            String allowed_sms_category= joinTableRows.getString(29);
            String allowed_send_otp= joinTableRows.getString(30);
            String newsletter= joinTableRows.getString(31);
            int	valid_email= joinTableRows.getInt(32);
            String user_status= joinTableRows.getString(33);
            String mobile_verification_status= joinTableRows.getString(34);
            String create_time= joinTableRows.getString(35);
            int allowschedule= joinTableRows.getInt(36);
            int	hygiene_check= joinTableRows.getInt(37);
            String custom_board_rack_id= joinTableRows.getString(38);

//            int id = joinTableRows.getInt(39);
//            int user_id = joinTableRows.getInt(40);
            String key_name = joinTableRows.getString(41);
            String value = joinTableRows.getString(42);
            String created_date = joinTableRows.getString(43);

            if (!userMap.containsKey(user_id)) {
                targetLastId++;

                userMap.put(user_id, targetLastId);

                insertMappingStmt.execute("insert into target.user_mapping"
                        + " (serial_no,old_id,new_id) values"
                        + " ('" + userMappingLast++ + "','" + user_id + "','" + targetLastId + "')");

                insertUserStmt.execute("insert into target.user"
                        + " (user_id, user_id_bcpkup, username, email, display_name, password, pin, state_id, gender, " +
                        "postalcode, user_type_id, mobile, phone, city, country_id, board_id, class_id, user_photo, dob, " +
                        "school_id, school_name, product_type, parent_id, age , address, ip, subscribe_me, allowed_email_category, " +
                        "allowed_sms_category, allowed_send_otp, newsletter, valid_email, user_status, mobile_verification_status, " +
                        "create_time, allowschedule, hygiene_check, custom_board_rack_id) values"
                        + " ('" + targetLastId + "','" + user_id_bcpkup + "','" + username + "','" + email + "','" + display_name + "','" + password + "'," +
                        "'" + pin + "','"  + state_id + "','" + gender + "' ,'" + postalcode + "','" + user_type_id + "', '" + mobile + "', '" + phone + "', '" + city + "',"+
                        "'" + country_id + "', '" + board_id + "', '" + class_id + "', '" + user_photo + "', '" + dob + "', '" + school_id + "', '" + school_name + "', '" + product_type + "', " +
                        "'" + parent_id + "','" + age + "' ,'" + address + "', '" + ip + "','" + subscribe_me + "','" + allowed_email_category + "','"+ allowed_sms_category + "', " +
                        "'" + allowed_send_otp + "', '" + newsletter + "', '" + valid_email + "', '" + user_status + "','" + mobile_verification_status + "','"+ create_time + "', " +
                        "'" + allowschedule + "', '" + hygiene_check + "', '" + custom_board_rack_id + "')");
            }

            int source_uod_freq = source_uod_map.get(user_id)==null ? 0 : source_uod_map.get(user_id);
            int target_uod_freq = target_uod_map.get(user_id)==null ? 0 : target_uod_map.get(user_id);

            while (source_uod_freq > target_uod_freq) {
                target_uod_freq++;
                target_uod_map.put(user_id,target_uod_freq);

                insertUODStmt.execute("insert into target.user_other_details"
                        + " (id,user_id,key_name, value,created_date) values"
                        + " ('" + targetForeignLastId++ + "','" + targetLastId + "','" + key_name + "','" + value + "','" + created_date + "')");
            }
        }
        final long migrationLatency = System.currentTimeMillis() - migrationStartTime ;
        System.out.println("Total Migration Time: " + migrationLatency);
    }
}