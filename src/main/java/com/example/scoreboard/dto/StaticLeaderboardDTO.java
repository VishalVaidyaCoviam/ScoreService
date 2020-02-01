package com.example.scoreboard.dto;
import com.example.scoreboard.document.ScoreStatic;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Data
public class StaticLeaderboardDTO {
    List<ScoreStatic> scoreStaticList = new ArrayList<ScoreStatic>();
    int rank;
}

