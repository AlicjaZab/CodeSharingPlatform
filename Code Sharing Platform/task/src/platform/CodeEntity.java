package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
public class CodeEntity {

     @Id
     @JsonIgnore
     String id;

     private String code;

     @JsonIgnore
     private LocalDateTime rawDate;

     private long time;

     private long views;

     @JsonIgnore
     @Nullable
     private LocalDateTime expDate = null;

     @JsonIgnore
     private boolean restrictedViews = false;

     private String date;

     CodeEntity() {
          id = UUID.randomUUID().toString();
          setRawDate(LocalDateTime.now().withNano(0));
     };

     public long getTime() {
          return time;
     }

     public void setTime(long time) {
          this.time = time;
     }

     public long getViews() {
          return views;
     }

     public void setViews(long views) {
          this.views = views;
     }

      public void update() {
          this.rawDate = LocalDateTime.now().withNano(0);
     }

     public  String getCode() {
          return code;
     }

     public  void setCode(String code) {
          this.code = code;
     }

     public  LocalDateTime getRawDate() {
          return rawDate;
     }

     public  void setRawDate(LocalDateTime updateDate) {
          this.rawDate = updateDate;

          String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
          date = rawDate.format(formatter);
     }

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public LocalDateTime getExpDate() {
          return expDate;
     }

     public void setExpDate(LocalDateTime expDate) {
          this.expDate = expDate;
     }

     public String getDate() {
          return date;
     }

     public void setDate(String date) {
          this.date = date;
     }

     public boolean isRestrictedViews() {
          return restrictedViews;
     }

     public void setRestrictedViews(boolean restrictedViews) {
          this.restrictedViews = restrictedViews;
     }

}
