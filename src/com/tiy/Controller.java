package com.tiy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{
	public TextField answerBox;
	public Label mathExpression;
	public Label TimerLabel;
	public Label percentageLabel;
	public Label wrongLabel;
	public Label correctLabel;
	public CheckBox divisionOp;
	public CheckBox multOp;
	public CheckBox subOp;
	public CheckBox addOp;
	private Timeline timeLine;
	private ArrayList<String> operators = new ArrayList<>();

	private int minutes;
	private int seconds;
	private int milliseconds;
	private Score score = new Score();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mathExpression.setText("Press Start to begin!");
		answerBox.setEditable(false);

	}

	public void submit(ActionEvent actionEvent) {
		System.out.println(answerBox.getText());
		int answer = 0;
		String[] equation = mathExpression.getText().split(" ");
		if(equation[1].equals("+")){
			answer=Integer.parseInt(equation[0])+Integer.parseInt(equation[2]);

	}
// else if(equation[1].equals("-")){
//			answer=Integer.parseInt(equation[0])-Integer.parseInt(equation[2]);
//		}else if(equation[1].equals("*")){
//			answer=Integer.parseInt(equation[0])*Integer.parseInt(equation[2]);
//		}else if(equation[1].equals("/")){
//			answer=Integer.parseInt(equation[0])/Integer.parseInt(equation[2]);
//		}


		checkAnswer(answer,Integer.parseInt(answerBox.getText()));

	}
	private void checkAnswer(int Answer,int userAnswer){
		DecimalFormat df = new DecimalFormat("#00");
		if(Answer == userAnswer){
			score.setCorrect(score.getCorrect()+1);
			correctLabel.setText(Integer.toString(score.getCorrect()));
		}else{
			score.setWrong(score.getWrong()+1);
			wrongLabel.setText(Integer.toString(score.getWrong()));
		}
		//TODO get percentages to work right
		score.setPercentage((score.getCorrect()/(score.getCorrect()+score.getWrong()))*100.00);
		System.out.println(score.getPercentage());
		percentageLabel.setText(df.format(score.getPercentage())+" %");
		equation();
		answerBox.clear();
	}

	public void Start(ActionEvent actionEvent) {
		equation();
		answerBox.setEditable(true);
		countDown();
	}
	private void equation(){
		String expression= (int)(Math.random()*50)+" + "+ (int)(Math.random()*50);
		mathExpression.setText(expression);
	}
	private void countDown(){
		minutes = 0;
		seconds = 60;
		milliseconds=1000;
		timeLine = new Timeline();
		timeLine.setCycleCount(Timeline.INDEFINITE);
		timeLine.getKeyFrames().add(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				milliseconds--;
				TimerLabel.setText(String.format("%1$02d",minutes)+":"+String.format("%1$02d",seconds));

				if(milliseconds<=0){
					if(minutes == 0 && seconds==0){

					}else{
						milliseconds = 1000;
						seconds--;
					}
				}
				if(seconds <= 0){
					if(minutes == 0){

					}else{
						seconds = 59;
						minutes--;
					}
				}
				if(minutes<= 0){
					if(seconds<=0){
						if(milliseconds <= 0);
						//TODO get seconds to display 00
						seconds =0;
						answerBox.setEditable(false);
						timeLine.stop();
					}
					if(seconds<=30){
						TimerLabel.setStyle("-fx-text-fill: red");
					}
				}
			}
		}));
		timeLine.playFromStart();
	}

//Todo Make work
	public void addOperator(ActionEvent actionEvent) {

		if (addOp.isSelected()){
			operators.add("+");
		}
		if (subOp.isSelected()){
			operators.add("-");
		}
		if (multOp.isSelected()){
			operators.add("*");
		}
		if (divisionOp.isSelected()){
			operators.add("/");
		}
	}
}
