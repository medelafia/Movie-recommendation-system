package com.backend.dto;

import com.backend.enums.HistoryType;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class History {
    private int contentId ;
    private String contentTitle ;
    private Date date;
    private Time time ;
    private HistoryType historyType;
}
