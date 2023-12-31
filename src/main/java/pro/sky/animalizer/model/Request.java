package pro.sky.animalizer.model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Класс Request для хранения в БД и обработки запросов телеграмм пользователей
 */
@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // уникальный id обращения

    @Column(name = "chatId")
    private Long chatId; // id телеграмм чата

    @Column(name = "requestTime")
    private LocalDateTime requestTime;  // дата и время обращения
    @Column(name = "requestText")
    private String requestText;  // текст обращения - с сутью обращения

    public Request(Long chatId, LocalDateTime requestTime, String requestText) {
        this.chatId = chatId;
        this.requestTime = requestTime;
        this.requestText = requestText;
    }

    public Request() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestText() {
        return requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request request = (Request) o;
        return Objects.equals(getId(), request.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", requestTime=" + requestTime +
                ", requestText='" + requestText + '\'' +
                '}';
    }
}