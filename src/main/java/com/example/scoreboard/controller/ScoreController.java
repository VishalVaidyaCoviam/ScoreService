package com.example.scoreboard.controller;

import com.example.scoreboard.document.*;
import com.example.scoreboard.dto.*;
import com.example.scoreboard.response.APIResponse;
import com.example.scoreboard.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/score")
public class ScoreController {


    @Autowired
    ScoreDynamicService scoreDynamicService;

    @Autowired
    ScoreStaticService scoreStaticService;


    @Autowired
    ScoreOverallService scoreOverallService;

    @Autowired
    TempScoreStaticService tempScoreStaticService;

    @Autowired
    TempScoreDynamicService tempScoreDynamicService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/getleaderboardstatic/{quizId}")
    public ResponseEntity<APIResponse<String>> leaderboardstatic(@RequestHeader(value="userId") String userId, @PathVariable("quizId") String quizId){
        StaticLeaderboardDTO staticLeaderboardDTO = new StaticLeaderboardDTO();
        staticLeaderboardDTO = scoreStaticService.getStaticLeaderboard(userId , quizId);
        APIResponse response = new APIResponse();
        response.setMessage("Rank");
        response.setStatus(true);
        response.setData(staticLeaderboardDTO);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/getleaderboarddynamic/{quizId}")
    public ResponseEntity<APIResponse<String>> leaderboarddynamic(@RequestHeader(value="userId") String userId, @PathVariable("quizId") String quizId){
        DynamicLeaderboardDTO dynamicLeaderboardDTO = new DynamicLeaderboardDTO();
        dynamicLeaderboardDTO = scoreDynamicService.getDynamicLeaderboard(userId , quizId);
        APIResponse response = new APIResponse();
        response.setMessage("Rank");
        response.setStatus(true);
        response.setData(dynamicLeaderboardDTO);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/getindividualoverall")
    public ResponseEntity<APIResponse<String>> getIndividualOverall(@RequestHeader(value="userId") String userId) {
            ScoreOverall scoreOverall = scoreOverallService.findOverallById(userId);
            APIResponse response = new APIResponse();
            response.setStatus(true);
            response.setMessage("good");
            response.setData(scoreOverall);
            return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/getindividualstatic/{quizId}")
    public ResponseEntity<APIResponse<String >> getStatic(@RequestHeader(value="userId") String userId, @PathVariable("quizId") String quizId) {
        APIResponse response = new APIResponse();
        ScoreStatic scoreStatic = scoreStaticService.findStatic(userId ,quizId);
        response.setData(scoreStatic);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/validateanswerstatic")
    public ResponseEntity<APIResponse<String>> validateStatic(@RequestBody ValidationDTO validationDTO,@RequestHeader(value="userId") String userId){
        validationDTO.setQuestionId(userId);
        final String uri = "http://172.16.20.15:8082/search/get/";
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<APIResponse<QuestionDTO>> parameterizedTypeReference = new ParameterizedTypeReference<APIResponse<QuestionDTO>>(){};
        APIResponse<QuestionDTO> responseElastic = restTemplate.exchange(uri + validationDTO.getQuestionId(), HttpMethod.GET, null, parameterizedTypeReference).getBody();
        QuestionDTO questionDTO = responseElastic.getData();
        int marks=1;
        if(questionDTO.getDifficulty().equalsIgnoreCase("medium"))
            marks = 2;
        else if(questionDTO.getDifficulty().equalsIgnoreCase("hard"))
            marks=3;
//
//
        if(validationDTO.getAnswer().equals(questionDTO.getAnswer())){
        System.out.println(userId);
            tempScoreStaticService.updateScore(validationDTO, marks);
            APIResponse response = new APIResponse();
            response.setStatus(true);
            response.setMessage("true");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else{
            APIResponse response = new APIResponse();
            response.setStatus(true);
            response.setMessage("false");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/validateanswerdynamic")
    public ResponseEntity<APIResponse<String >> validateDynamic(@RequestBody ValidationDTO validationDTO,@RequestHeader(value="userId") String userId) {
        validationDTO.setUserId(userId);
         Date date = new Date(System.currentTimeMillis());
//        Timestamp timestamp = new Timestamp(date.getTime());
        final String uri = "http://172.16.20.15:8082/search/get/";
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<APIResponse<QuestionDTO>> parameterizedTypeReference = new ParameterizedTypeReference<APIResponse<QuestionDTO>>(){};
        APIResponse<QuestionDTO> responseElastic = restTemplate.exchange(uri + validationDTO.getQuestionId(), HttpMethod.GET, null, parameterizedTypeReference).getBody();
        QuestionDTO questionDTO = responseElastic.getData();
        int marks=1;
        if(questionDTO.getDifficulty().equalsIgnoreCase("medium"))
            marks = 2;
        else if(questionDTO.getDifficulty().equalsIgnoreCase("hard"))
            marks=3;
        SendQuestionDTO sendQuestionDTO = new SendQuestionDTO();
        sendQuestionDTO.setUserId(userId);
        sendQuestionDTO.setQuizId(validationDTO.getQuizId());
        if(validationDTO.getAnswer().equals(questionDTO.getAnswer())){
            tempScoreDynamicService.updateScore(validationDTO  , marks , date,sendQuestionDTO);
//        tempScoreDynamicService.updateScore(validationDTO  , marks ,sendQuestionDTO);
            APIResponse response = new APIResponse();
            response.setStatus(true);
            response.setMessage("true");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else{
            APIResponse response = new APIResponse();
            response.setStatus(true);
            response.setMessage("false");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/submitdynamic")
    public ResponseEntity<APIResponse<String>> submitDynamicQuiz(@RequestBody SubmitDTO submitDynamicDTO,@RequestHeader(value="userId") String userId){
        submitDynamicDTO.setUserId(userId);
        APIResponse response = new APIResponse();
        scoreDynamicService.submitScore(submitDynamicDTO);
        response.setStatus(true);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/submitstatic/{skip}")
    public ResponseEntity<APIResponse<String>> submitStaticQuiz(@RequestBody SubmitDTO submitDTO, @PathVariable("skip") boolean skip,@RequestHeader(value="userId") String userId){
        submitDTO.setUserId(userId);
        APIResponse response = new APIResponse();
        response.setStatus(scoreStaticService.submitScore(submitDTO, skip));

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/overallleaderboard")
    public  ResponseEntity<APIResponse<String>> overallLeaderBoard(){
        APIResponse response = new APIResponse();
        List<ScoreOverall> leaderboard;
        leaderboard = scoreOverallService.findAll();
        response.setStatus(true);
        response.setMessage("leaderboard");
        response.setData(leaderboard);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/getQuestionId")
    String sendQuestion(@RequestBody  SendQuestionDTO sendQuestionDTO)
    {
        String tp = tempScoreDynamicService.sendQuestion(sendQuestionDTO);
        System.out.println(tp);
        return tp;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/fastestFingerFirsrt/{quizId}")
    void fastestFingerFirst(@PathVariable("quizId") String quizId)
    {
        tempScoreDynamicService.fastestFingerFirst(quizId);
    }

}
