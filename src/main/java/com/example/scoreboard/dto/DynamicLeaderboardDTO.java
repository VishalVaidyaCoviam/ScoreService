
package com.example.scoreboard.dto;
import com.example.scoreboard.document.ScoreDynamic;
import com.example.scoreboard.document.ScoreStatic;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Data
public class DynamicLeaderboardDTO {
    List<ScoreDynamic> scoreDynamicList = new ArrayList<ScoreDynamic>();
    int rank;
}

