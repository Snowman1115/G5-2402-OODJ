package com.project.dao;

import com.project.common.constants.MessageConstant;
import com.project.common.utils.*;
import com.project.pojo.ProjectModule;
import static java.lang.Integer.parseInt;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.*;

@Slf4j
public class ModuleDAO {

    private static final String MODULE_DATA = PropertiesReader.getProperty("ModuleData");
    private static List<ProjectModule> modules = new ArrayList<>();

    static {
        loadConsultationData();
    }

    /**
     * Get Module by Id
     * @param moduleId
     * @return Module
     */
    public ProjectModule getModuleById(Integer moduleId) {
        for (ProjectModule module : modules) {
            if (module.getModuleId().equals(moduleId)) {
                return module;
            }
        }
        return null;
    }

    /**
     * return all modules based on intake id
     * @param intakeId
     * @return
     */
    public List<Integer> getModulesByIntakeId(Integer intakeId) {
        List<Integer> modulesList = new ArrayList<>();
        for (ProjectModule module : modules) {
            if (module.getIntakeId() == intakeId) {
                modulesList.add(module.getModuleId());
            }
        }
        return modulesList;
    }

    public List<ProjectModule> getAllModules() {
        return modules;
    }

    /**
     * Get Module by lecturerId
     * @param lecturerId
     * @return List of Module
     */
    
    // Method to get all module details with first marker ID
    public List getModuleByFirstMarkerId(Integer lecturerId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (ProjectModule module : modules) {
            if (module.getFirstMarker().equals(lecturerId)) {
                Map map = new HashMap<>();
                map.put("id", module.getModuleId().toString());
                map.put("intakeId", module.getIntakeId().toString());
                map.put("moduleCode", module.getModuleCode());
                map.put("supervisorId", module.getSupervisorId().toString());
                map.put("firstMarker", module.getFirstMarker().toString());
                map.put("secondMarker", module.getSecondMarker().toString());
                map.put("startDate", DateTimeUtils.formatStrDate(module.getStartDate()));
                map.put("endDate", DateTimeUtils.formatStrDate(module.getEndDate()));
                map.put("created_at", DateTimeUtils.formatStrDateTime(module.getCreatedAt()));
                map.put("updated_at", DateTimeUtils.formatStrDateTime(module.getUpdatedAt()));
                list.add(map);
            }
        }
        return list;
    }
    
    //This method is similar to the above ones
    //The only difference is with second marker ID
    public List getModuleBySecondMarkerId(Integer lecturerId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (ProjectModule module : modules) {
            if (module.getSecondMarker().equals(lecturerId)) {
                Map map = new HashMap<>();
                map.put("id", module.getModuleId().toString());
                map.put("intakeId", module.getIntakeId().toString());
                map.put("moduleCode", module.getModuleCode());
                map.put("supervisorId", module.getSupervisorId().toString());
                map.put("firstMarker", module.getFirstMarker().toString());
                map.put("secondMarker", module.getSecondMarker().toString());
                map.put("startDate", DateTimeUtils.formatStrDate(module.getStartDate()));
                map.put("endDate", DateTimeUtils.formatStrDate(module.getEndDate()));
                map.put("created_at", DateTimeUtils.formatStrDateTime(module.getCreatedAt()));
                map.put("updated_at", DateTimeUtils.formatStrDateTime(module.getUpdatedAt()));
                list.add(map);
            }
        }
        return list;
    }

     /**
     * Get Module by lecturerId
     * @param lecturerId
     * @return List of Module
     */
    public List<ProjectModule> getModuleListByFirstMarkerId(Integer lecturerId)
    {
        List<ProjectModule> list = new ArrayList<>();
        for (ProjectModule module : modules) {
            if (module.getFirstMarker().equals(lecturerId)) {
                list.add(module);
            }
        }
        return list;
    }

    public List<ProjectModule> getModuleListBySecondMarkerId(Integer lecturerId)
    {
        List<ProjectModule> list = new ArrayList<>();
        for (ProjectModule module : modules) {
            if (module.getSecondMarker().equals(lecturerId)) {
                list.add(module);
            }
        }
        return list;
    }


    public void addModule(Integer intakeId, String moduleCode, Integer projectManagerId, LocalDate startDate, LocalDate endDate) {
        JsonHandler moduleJson = new JsonHandler();
        List<Integer> idList = new ArrayList<>();
        IDGenerator idGenerator = new LongIDGenerator();

        moduleJson.encode(FileHandler.readFile(MODULE_DATA));

        for (ProjectModule m : modules) {
            idList.add(m.getModuleId());
        }
        int newModuleId = idGenerator.generateNewID(idList);

        ProjectModule newModule = new ProjectModule();
        newModule.setModuleId(newModuleId);
        newModule.setIntakeId(intakeId);
        newModule.setModuleCode(moduleCode);
        newModule.setSupervisorId(projectManagerId);
        newModule.setFirstMarker(0);
        newModule.setSecondMarker(0);
        newModule.setStartDate(startDate);
        newModule.setEndDate(endDate);
        newModule.setCreatedAt(LocalDateTime.now());
        newModule.setUpdatedAt(LocalDateTime.now());
        modules.add(newModule);

        JSONObject newModuleObj = new JSONObject();
        newModuleObj.put("id", newModuleId);
        newModuleObj.put("intakeId", intakeId);
        newModuleObj.put("moduleCode", moduleCode);
        newModuleObj.put("supervisorId", projectManagerId);
        newModuleObj.put("firstMarkerId", 0);
        newModuleObj.put("secondMarkerId", 0);
        newModuleObj.put("startDate", DateTimeUtils.formatStrDate(startDate));
        newModuleObj.put("endDate", DateTimeUtils.formatStrDate(endDate));
        newModuleObj.put("created_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
        newModuleObj.put("updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
        moduleJson.addObject(newModuleObj, MODULE_DATA);
    }
    // Jin Xun - Get Project Manager ID
    public List getModuleByProjectManagerId(Integer ProjectManagerId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (ProjectModule module : modules) {
            if (module.getSupervisorId().equals(ProjectManagerId)) {
                Map map = new HashMap<>();
                map.put("id", module.getModuleId().toString());
                map.put("intakeId", module.getIntakeId().toString());
                map.put("moduleCode", module.getModuleCode());
                map.put("supervisorId", module.getSupervisorId().toString());
                map.put("firstMarker", module.getFirstMarker().toString());
                map.put("secondMarker", module.getSecondMarker().toString());
                map.put("startDate", DateTimeUtils.formatStrDate(module.getStartDate()));
                map.put("endDate", DateTimeUtils.formatStrDate(module.getEndDate()));
                map.put("created_at", DateTimeUtils.formatStrDateTime(module.getCreatedAt()));
                map.put("updated_at", DateTimeUtils.formatStrDateTime(module.getUpdatedAt()));
                list.add(map);
            }
        }
        return list;
    }

       public List getModuleByModuleId(Integer moduleId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (ProjectModule module : modules) {
            if (module.getModuleId().equals(moduleId)) {
                 Map map = new HashMap<>();
                map.put("id", module.getModuleId().toString());
                map.put("intakeId", module.getIntakeId().toString());
                map.put("moduleCode", module.getModuleCode());
                map.put("supervisorId", module.getSupervisorId().toString());
                map.put("firstMarker", module.getFirstMarker().toString());
                map.put("secondMarker", module.getSecondMarker().toString());
                map.put("startDate", DateTimeUtils.formatStrDate(module.getStartDate()));
                map.put("endDate", DateTimeUtils.formatStrDate(module.getEndDate()));
                map.put("created_at", DateTimeUtils.formatStrDateTime(module.getCreatedAt()));
                map.put("updated_at", DateTimeUtils.formatStrDateTime(module.getUpdatedAt()));
                list.add(map);
            }
        }
        return list;
    }

       
    /**
     * Save Supervisor and Second Marker In Module 
     * @param moduleDetails
     * @return 
     */
    public Boolean saveModuleChanges(List<String> moduleDetails) {
        for (ProjectModule module : modules) {
            if (module.getModuleId().equals(parseInt(moduleDetails.get(0)))) {
                System.out.println(moduleDetails.get(0));
                System.out.println(moduleDetails.get(1));
                update(parseInt(moduleDetails.get(0)), "firstMarker", moduleDetails.get(1));
                update(parseInt(moduleDetails.get(0)), "secondMarker", moduleDetails.get(2));
                update(parseInt(moduleDetails.get(0)), "updated_at", DateTimeUtils.formatStrDateTime(LocalDateTime.now()));
                return true;
            }
        }
        return false;
    }

    /**
     * Preload Data into presentations Array
     */
    private static void loadConsultationData() {
        JsonHandler userData = new JsonHandler();
        userData.encode(FileHandler.readFile(MODULE_DATA));

        for (int i = 0; i < (userData.getAll().size()); i++) {
            JsonHandler obj = new JsonHandler();
            obj.setObject(userData.getObject(i));

            ProjectModule projectModule = new ProjectModule();
            projectModule.setModuleId(obj.getInt("id"));
            projectModule.setIntakeId(obj.getInt("intakeId"));
            projectModule.setModuleCode(obj.get("moduleCode"));
            projectModule.setSupervisorId(obj.getInt("supervisorId"));
            projectModule.setFirstMarker(obj.getInt("firstMarkerId"));
            projectModule.setSecondMarker(obj.getInt("secondMarkerId"));
            projectModule.setStartDate(DateTimeUtils.formatDate(obj.get("startDate")));
            projectModule.setEndDate(DateTimeUtils.formatDate(obj.get("endDate")));
            projectModule.setCreatedAt(DateTimeUtils.formatDateTime(obj.get("created_at")));
            projectModule.setUpdatedAt(DateTimeUtils.formatDateTime(obj.get("updated_at")));

            modules.add(projectModule);
        }
    }

   

    // Update consultation data
    public static boolean update(Integer moduleId, String field, String value) {
        // System.out.println(value);
        for (ProjectModule module : modules) {
            if (module.getModuleId().equals(moduleId)) {
                try {
                    switch (field) {
                        case "intakeId" -> {
                            module.setModuleId(Integer.parseInt(value));
                            return store(moduleId, "intakeId", value);
                        }
                        case "moduleCode" -> {
                            module.setModuleCode(String.valueOf(value));
                            return store(moduleId, "moduleCode", value);
                        }
                        case "supervisorId" -> {
                            module.setSupervisorId(Integer.parseInt(value));
                            return store(moduleId, "supervisorId", value);
                        }
                        case "firstMarker" -> {
                            module.setFirstMarker(Integer.parseInt(value));
                            return store(moduleId, "firstMarkerId", value);
                        }
                        case "secondMarker" -> {
                            module.setSecondMarker(Integer.parseInt(value));
                            return store(moduleId, "secondMarkerId", value);
                        }
                        case "startDate" -> {
                            module.setStartDate(DateTimeUtils.formatDate(value));
                            return store(moduleId, "startDate", value);
                        }
                        case "endDate" -> {
                            module.setEndDate(DateTimeUtils.formatDate(value));
                            return store(moduleId, "endDate", value);
                        }
                        case "updated_at" -> {
                            module.setUpdatedAt(DateTimeUtils.formatDateTime(value));
                            return store(moduleId, "updated_at", value);
                        }
                        default -> {
                            log.info("Error: " + MessageConstant.ERROR_OBJECT_FIELD_NOT_FOUND);
                            return false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        log.info("Error: " + MessageConstant.ERROR_OBJECT_NOT_FOUND + value);
        return false;

    }

    /**
     * Store updated data into text file
     * @param consultationId
     * @param attribute
     * @param value
     * @return
     */
    private static boolean store(Integer consultationId, String attribute, String value) {
        // System.out.println(consultationId + attribute + value);
        JsonHandler userJson = new JsonHandler();
        userJson.encode(FileHandler.readFile(MODULE_DATA));
        return userJson.update(consultationId, attribute, value, MODULE_DATA);
    }


}
