/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cons;

import java.io.Serializable;

/**
 *
 * @author matij
 */
public enum Operation implements Serializable {
    LOGIN,
    REGISTER,
    ADD_APPLICATION,   
    GET_ALL_APPLICATIONS,  
    CANCEL_APPLICATION,   
    EDIT_APPLICATION,     
    EXIT,
    GET_USER_APPLICATIONS
}
