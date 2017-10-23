package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by devmac on 02/09/16.
 */
public class TaskData implements Serializable{
    @SerializedName("product_id")
    private String productId;
    @SerializedName("task_id")
    private String taskId;
    @SerializedName("question")
    private String question;
    @SerializedName("answer_type")
    private String answerType;
    @SerializedName("correct_answer")
    private String correctAnswer;
    @SerializedName("attachment_url")
    private String attachmentUrl;
    @SerializedName("receipe_name")
    private String receipeName;
    @SerializedName("receipe_description")
    private String receipeDescription;
    @SerializedName("fact_title")
    private String factTitle;
    @SerializedName("fact_content")
    private String factContent;
    @SerializedName("video_url")
    private String videoUrl;
    @SerializedName("picture_url")
    private String pictureUrl;
    @SerializedName("dogooder_content")
    private String dogooderContent;
    @SerializedName("dogooder_title")
    private String dogooderTitle;
    @SerializedName("answers")
    private List<String> answers;

    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private List<String> images;
    @SerializedName("image_title")
    private List<String> images_title;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getReceipeName() {
        return receipeName;
    }

    public void setReceipeName(String receipeName) {
        this.receipeName = receipeName;
    }

    public String getReceipeDescription() {
        return receipeDescription;
    }

    public void setReceipeDescription(String receipeDescription) {
        this.receipeDescription = receipeDescription;
    }

    public String getFactTitle() {
        return factTitle;
    }

    public void setFactTitle(String factTitle) {
        this.factTitle = factTitle;
    }

    public String getFactContent() {
        return factContent;
    }

    public void setFactContent(String factContent) {
        this.factContent = factContent;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDogooderContent() {
        return dogooderContent;
    }

    public void setDogooderContent(String dogooderContent) {
        this.dogooderContent = dogooderContent;
    }

    public String getDogooderTitle() {
        return dogooderTitle;
    }

    public void setDogooderTitle(String dogooderTitle) {
        this.dogooderTitle = dogooderTitle;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getImages_title() {
        return images_title;
    }

    public void setImages_title(List<String> images_title) {
        this.images_title = images_title;
    }
}