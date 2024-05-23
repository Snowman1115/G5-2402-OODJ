package com.project.dao;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.*;
import com.project.pojo.InvestigateReport;
import com.project.pojo.ModuleFeedback;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ModuleFeedbackDAO {

    private static final String FEEDBACK_DATA = PropertiesReader.getProperty("ModuleFeedbackData");
    private static List<ModuleFeedback> feedbacks = new ArrayList<>();

    static {
        loadConsultationData();
    }

    private static List<Integer> getAllFeedbackId() {
        List<Integer> list = new ArrayList<>();
        for (ModuleFeedback feedback : feedbacks) {
            list.add(feedback.getFeedbackId());
        }
        return list;
    }

    public List<ModuleFeedback> getAllFeedbackByStudentId(Integer studentId) {
        List<ModuleFeedback> lists = new ArrayList<>();
        for (ModuleFeedback feedback : feedbacks) {
            if (feedback.getStudentId().equals(studentId)) {
                lists.add(feedback);
            }
        }
        return lists;
    }

    /**
     * Preload Data into presentations Array
     */
    private static void loadConsultationData() {
        JsonHandler userData = new JsonHandler();
        userData.encode(FileHandler.readFile(FEEDBACK_DATA));

        for (int i = 0; i < (userData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.setObject(userData.getObject(i));

            ModuleFeedback moduleFeedback = new ModuleFeedback();
            moduleFeedback.setFeedbackId(obj.getInt("id"));
            moduleFeedback.setModuleId(obj.getInt("moduleId"));
            moduleFeedback.setStudentId(obj.getInt("studentId"));
            moduleFeedback.setComments(obj.get("comments"));

            feedbacks.add(moduleFeedback);
        }
    }

    public Boolean submitFeedback(Integer studentId, Integer moduleId, String commets) {
        ModuleFeedback moduleFeedback = new ModuleFeedback();
        IDGenerator idGenerator = new ShortIDGenerator();
        moduleFeedback.setFeedbackId(idGenerator.generateNewID(getAllFeedbackId()));
        moduleFeedback.setStudentId(studentId);
        moduleFeedback.setModuleId(moduleId);
        moduleFeedback.setComments(commets);
        feedbacks.add(moduleFeedback);


        JSONObject newFeedbackJSON = new JSONObject();
        newFeedbackJSON.put("id", moduleFeedback.getFeedbackId());
        newFeedbackJSON.put("moduleId", moduleFeedback.getModuleId());
        newFeedbackJSON.put("studentId", moduleFeedback.getStudentId());
        newFeedbackJSON.put("comments", moduleFeedback.getComments());

        JsonHandler reportJSON = new JsonHandler();
        reportJSON.encode(FileHandler.readFile(FEEDBACK_DATA));
        reportJSON.addObject(newFeedbackJSON, FEEDBACK_DATA);

        return true;
    }


    /**
     * Store updated data into text file
     * @param consultationId
     * @param attribute
     * @param value
     * @return
     */
    private static boolean store(Integer consultationId, String attribute, String value) {
        JsonHandler userJson = new JsonHandler();
        userJson.encode(FileHandler.readFile(FEEDBACK_DATA));
        return userJson.update(consultationId, attribute, value, FEEDBACK_DATA);
    }


}
