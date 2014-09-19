package org.opencb.opencga.catalog.core.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.opencb.datastore.core.ObjectMap;
import org.opencb.datastore.core.QueryResult;
import org.opencb.opencga.catalog.core.beans.*;
import org.opencb.opencga.catalog.core.io.CatalogIOManagerException;

import java.io.IOException;
import java.nio.file.Path;

public interface CatalogDBAdaptor {

    /**
     * User methods ···
     * ***************************
     */
    boolean checkUserCredentials(String userId, String sessionId);

    QueryResult<User> createUser(User user) throws CatalogManagerException, JsonProcessingException;

    QueryResult deleteUser(String userId) throws CatalogManagerException, JsonProcessingException;


    QueryResult<ObjectMap> login(String userId, String password, Session session) throws CatalogManagerException, IOException;

    QueryResult logout(String userId, String sessionId) throws CatalogManagerException, IOException;

    QueryResult<ObjectMap> loginAsAnonymous(Session session) throws CatalogManagerException, IOException;

    QueryResult logoutAnonymous(String sessionId);


    // public String getUserByAccountId(String accountId, String sessionId);

    // public String getUserByEmail(String email, String sessionId);

    QueryResult<User> getUser(String userId, String lastActivity) throws CatalogManagerException;

    QueryResult changePassword(String userId, String oldPassword, String newPassword) throws CatalogManagerException;

    QueryResult changeEmail(String userId, String newEmail) throws CatalogManagerException;

    QueryResult resetPassword(String userId, String email) throws CatalogManagerException;

//    public boolean checkSessionId(String userId, String sessionId);

    QueryResult getSession(String userId, String sessionId) throws IOException;

    String getUserIdBySessionId(String sessionId);

    /**
     * Project methods ···
     * ***************************
     */
    QueryResult<Project> createProject(String userId, Project project) throws CatalogManagerException, JsonProcessingException;

    QueryResult<Project> getAllProjects(String userId, String sessionId) throws CatalogManagerException;

    QueryResult<Project> getProject(int project) throws CatalogManagerException;

    QueryResult deleteProject(int projecetId) throws CatalogManagerException;

    QueryResult renameProject(int projectId, String newprojectName) throws CatalogManagerException;

    int getProjectId(String userId, String project) throws CatalogManagerException;

    String getProjectOwner(int projectId) throws CatalogManagerException;

    /**
     * Study methods ···
     * ***************************
     */

    QueryResult<Study> createStudy(int projectId, Study study) throws CatalogManagerException;

    QueryResult<Study> getAllStudies(int projectId) throws CatalogManagerException;
    QueryResult<Study> getAllStudies(String userId, String projectAlias) throws CatalogManagerException;

    QueryResult<Study> getStudy(int studyId) throws CatalogManagerException;

    QueryResult renameStudy(String userId, String projectAlias, String studyAlias, String newStudyName) throws CatalogManagerException;

    QueryResult renameStudy(int studyId, String newStudyName) throws CatalogManagerException;

    QueryResult deleteStudy(String userId, String projectAlias, String studyAlias) throws CatalogManagerException;

    QueryResult deleteStudy(int studyId) throws CatalogManagerException;

    int getStudyId(String userId, String projectAlias, String studyAlias) throws CatalogManagerException, IOException;

    int getProjectIdByStudyId(int studyId) throws CatalogManagerException;

    String getStudyOwner(int studyId) throws CatalogManagerException;

    /**
     * File methods ···
     * ***************************
     */

    // add file to study
    QueryResult<File> createFileToStudy(String userId, String projectAlias, String studyAlias, File file) throws CatalogManagerException;
    QueryResult<File> createFileToStudy(int studyId, File file) throws CatalogManagerException;

    QueryResult deleteFile(String userId, String projectAlias, String studyAlias, Path filePath) throws CatalogManagerException, IOException;
    QueryResult deleteFile(int studyId, Path filePath) throws CatalogManagerException;
    QueryResult deleteFile(int fileId) throws CatalogManagerException;

    QueryResult deleteFilesFromStudy(String userId, String projectAlias, String studyAlias, String sessionId) throws CatalogManagerException;
    QueryResult deleteFilesFromStudy(int studyId, String studyAlias, String sessionId) throws CatalogManagerException;

    int getFileId(String userId, String projectAlias, String studyAlias, Path filePath) throws CatalogManagerException, IOException;
    int getFileId(int studyId, Path filePath) throws CatalogManagerException, IOException;

    QueryResult<File> getFile(String userId, String projectAlias, String studyAlias, Path filePath) throws CatalogManagerException, IOException;
    QueryResult<File> getFile(int studyId, Path filePath) throws CatalogManagerException, IOException;
    QueryResult<File> getFile(int fileId) throws CatalogManagerException;

    QueryResult setFileStatus(String userId, String projectAlias, String studyAlias, Path filePath, String status, String sessionId) throws CatalogManagerException, IOException;
    QueryResult setFileStatus(int studyId, Path filePath, String status, String sessionId) throws CatalogManagerException, IOException;
    QueryResult setFileStatus(int fileId, String status, String sessionId) throws CatalogManagerException, IOException;

    int getStudyIdByFileId(int fileId) throws CatalogManagerException;
    String getFileOwner(int fileId) throws CatalogManagerException;

    // TODO: void shareObject(String userId, int fileId, Acl acl) throws CatalogManagerException;



    /**
     * Analysis methods ···
     * ***************************
     */

    QueryResult<Analysis> getAllAnalysis(String userId, String projectAlias, String studyAlias) throws CatalogManagerException;
    QueryResult<Analysis> getAllAnalysis(int studyId) throws CatalogManagerException;

    QueryResult createAnalysis(String userId, String projectAlias, String studyAlias, Analysis analysis) throws CatalogManagerException, IOException;
    QueryResult createAnalysis(int studyId, Analysis analysis) throws CatalogManagerException, JsonProcessingException;

    QueryResult<Job> createJob(int studyId, String analysisName, Job job, String sessionId) throws CatalogManagerException, JsonProcessingException;

    QueryResult deleteJob(int jobId, String sessionId) throws CatalogManagerException;

    Job getJob(int jobId, String sessionId) throws CatalogManagerException, IOException;

    String getJobStatus(int jobId, String sessionId) throws CatalogManagerException, IOException;

    void incJobVisites(int jobId, String sessionId) throws CatalogManagerException, IOException;

    void setJobCommandLine(int jobId, String commandLine, String sessionId) throws CatalogManagerException, IOException;

//    int getJobIndex(String userId, String jobId, String sessionId) throws CatalogManagerException, IOException;

//    Project getJobProject(String userId, String jobId, String sessionId) throws CatalogManagerException, IOException;




    /**
     * Util methods ···
     * ***************************
     */
//    List<AnalysisPlugin> getUserAnalysis(String sessionId) throws CatalogManagerException, IOException;
//
//    List<Bucket> jsonToBucketList(String json) throws IOException;
//
//    ObjectItem getObjectFromBucket(String userId, String bucketId, Path objectId, String sessionId) throws CatalogManagerException, IOException;



}
