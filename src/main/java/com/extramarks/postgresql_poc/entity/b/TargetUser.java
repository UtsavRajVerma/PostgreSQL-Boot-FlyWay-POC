package com.extramarks.postgresql_poc.entity.b;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "user",schema = "target")
public class TargetUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int user_id;

    private int user_id_bcpkup;

    private String username;

    private String email;

    private String	display_name;

    private String	password;

    private String	pin;

    private String state_id;

    private String	gender;

    private String	postalcode;

    private int	user_type_id;

    private String	mobile;

    private String	phone;

    private String	city;

    private String	country_id;

    private String	board_id;

    private String	class_id;

    private String	user_photo;

    private String	dob;

    private String	school_id;

    private String	school_name;

    private String	product_type;

    private String	parent_id;

    private String	age;

    private String	address;

    private String	ip;

    private String subscribe_me;

    private String	allowed_email_category;

    private String	allowed_sms_category;

    private String	allowed_send_otp;

    private String	newsletter;

    private int	valid_email;

    private String	user_status;

    private String	mobile_verification_status;

    private String	create_time;

    private int allowschedule;

    private int	hygiene_check;

    private String custom_board_rack_id;
}
