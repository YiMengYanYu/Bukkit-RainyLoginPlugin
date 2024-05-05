package com.rainy.entity;

/**
 * @author YiMeng
 * @DateTime: 2024/4/25 上午8:11
 * @Description: ws 返回结果
 */
public class Result {

   private String msg;
   private MsgType msgType;
   private String senderName;
   private String senderId;




   public Result() {
   }

   public Result(String msg, MsgType msgType, String senderName, String senderId) {
      this.msg = msg;
      this.msgType = msgType;
      this.senderName = senderName;
      this.senderId = senderId;
   }

   @Override
   public String toString() {
      return "Result{" +
              "msg='" + msg + '\'' +
              ", msgType=" + msgType +
              ", senderName='" + senderName + '\'' +
              ", senderId='" + senderId + '\'' +
              '}';
   }

   public String getMsg() {
      return msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public MsgType getMsgType() {
      return msgType;
   }

   public void setMsgType(MsgType msgType) {
      this.msgType = msgType;
   }

   public String getSenderName() {
      return senderName;
   }

   public void setSenderName(String senderName) {
      this.senderName = senderName;
   }

   public String getSenderId() {
      return senderId;
   }

   public void setSenderId(String senderId) {
      this.senderId = senderId;
   }
}
