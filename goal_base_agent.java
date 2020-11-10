package com.company;

import java.util.ArrayList;
import java.util.Random;

public class goal_base_agent {
    static int food_location_y;
    static int food_location_x;
    static ArrayList<Position> positions;
    static int environment_size;
    static int direction;
    static  String Rules,Actions;
    public static String utility(int food_locationx,int food_loctiony,ArrayList<Position> position,int environment_Size,int current_direction){
        positions=position;
        food_location_x=food_locationx;
        food_location_y=food_loctiony;
        environment_size=environment_Size;
        direction=current_direction;
        Rules=rules();
        Actions=check_action(Rules);
        int index=Actions.length();
        System.out.println(Rules +"\tRules\tAction\t"+Actions);
        for(int i=0;i<Actions.length();i++){
            if (Actions.charAt(i) == '0') {
              index=i;

            }
        }
        return String.valueOf(Rules.charAt(index));

    }
    public static String rules(){
        int x=food_location_x-positions.get(0).getX_init();
        int y=food_location_y-positions.get(0).getY_init();
        String  actions="";
        boolean rule_get=false;

         if(x<0){
                if(direction==2||direction==3||direction==1)
                {
                        rule_get=true;
                        actions+="1";
                }
        }
       else if(x>0){
            if(direction==2||direction==3||direction==0)
            {
                rule_get=true;
                actions+="0";
            }
        }

         if(y>0){
           if(direction==0||direction==1||direction==3) {
               rule_get=true;
                actions+="3";
           }
        }
       else if(y<0){
            if(direction==0||direction==1||direction==2) {
                rule_get=true;
                    actions+="2";
            }
        }
       if(!rule_get){
           if(y==0 &&  food_location_x -   positions.get(0).getX_init()   >  food_location_x   -  positions.get(0).getX_pre()   && direction!=0){
               actions+="32";
           }else if(y==0   && food_location_x  - positions.get(0).getX_init() <    food_location_x -    positions.get(0).getX_pre()    &&  direction!=1){
               actions+="32";
           }else if (x==0 && food_location_y - positions.get(0).getY_init() < food_location_y - positions.get(0).getY_pre()   &&  direction!=2){
               actions+="01";
           }else if (x==0 && food_location_y - positions.get(0).getY_init() > food_location_y - positions.get(0).getY_pre()   &&  direction!=3){
               actions+="01";
           }
       }

        return actions;
    }
public static  String check_action(String rules) {
    String action = "";
    int interval = 20;
    int head_positionx = 0;
    int head_positiony = 0;
    boolean game_over_state = false;
    for (int i = 0; i < rules.length(); i++) {
        game_over_state = false;
        head_positionx = positions.get(0).getX_init();
        head_positiony = positions.get(0).getY_init();
        int count=-1;
        if (rules.charAt(i)=='0') {//right
            head_positionx += interval;
            // count=check_action_step(0,2);
        } else if (rules.charAt(i)=='1') {//left
            head_positionx -= interval;
            // count=check_action_step(1,3);
        } else if (rules.charAt(i)=='2') {//up
            head_positiony -= interval;
           // count=check_action_step(2,3);
        } else if (rules.charAt(i)=='3') {//down
            head_positiony += interval;
            //count=check_action_step(3,3);
        }
         for (int j = 1; j < positions.size(); j++) {//head hit body
            if (head_positionx == positions.get(j).getX_init() && head_positiony == positions.get(j).getY_init()) {
                action += "1";
                game_over_state = true;
                break;
            }
        }
      /* if (count >= 0) {
            action += "1";
            game_over_state = true;

        }*/

        if (!game_over_state && head_positiony < 0 || head_positionx < 0 || head_positiony > environment_size || head_positionx > environment_size) {//GOING OUTSIDE CANVAS
            action += "1";
            game_over_state = true;

        }
        if (!game_over_state) {
            action += "0";
        }
        Actions=action;
    }
    System.out.println(Actions);
    if (action.contains("0")) {
      if (rules.length()>1){
      int []count=new int[4];
        char []act=action.toCharArray();
        if (rules.contains("1") && rules.contains("0")){
            if(Actions.charAt(rules.indexOf("1"))=='0' && Actions.charAt(rules.indexOf("0"))=='0'){//left or right
               count[0]=check_action_step(0);
                count[1]=check_action_step(1);
                if (count[0]>=count[1]){
                    act[rules.indexOf("1")]='1';
                    System.out.println("test13");
                }else {
                    act[rules.indexOf("0")]='1';
                    System.out.println("test10");
                }
             /*   int head_x=positions.get(0).getX_init();
                int tail_x=positions.get(0).getX_init();
                if(head_x-tail_x>0){//right
                    act[rules.indexOf("1")]='1';
                }else{
                    act[rules.indexOf("0")]='1';
                }*/
            }
        }
     if(rules.contains("2") && rules.contains("3") ){
         if (Actions.charAt(rules.indexOf("2"))=='0' && Actions.charAt(rules.indexOf("3"))=='0'){
             count[2]=check_action_step(2);
             count[3]=check_action_step(3);
             if(count[2]>=count[3]){
                 act[rules.indexOf("3")]='1';
                 System.out.println("test12"+count[2]+"\t"+count[3]);
             }else{
                 act[rules.indexOf("2")]='1';
                 System.out.println("test123"+count[2]+"\t"+count[3]);
             }
            /* int head_y=positions.get(0).getY_init();
             int tail_y=positions.get(0).getY_init();
             if (head_y-tail_y>0){
                 act[rules.indexOf("3")]='1';
             }else{
                 act[rules.indexOf("2")]='1';
             }*/
         }
     }
     if(rules.contains("0")||rules.contains("1") && rules.contains("2")||rules.contains("3")){
         System.out.println("teste234");
         int rl=-1;
         int ud=-1;
         if(rules.contains("0")){
             if(action.charAt(rules.indexOf("0"))=='0'){
                 rl=check_action_step(0);
             }
         }else if(rules.contains("1")){
             if(action.charAt(rules.indexOf("1"))=='0'){
                 rl=check_action_step(1);
             }
         }
         if(rules.contains("2")){
             if(action.charAt(rules.indexOf("2"))=='0'){
                 ud=check_action_step(2);
             }
         } else if(rules.contains("3")){
             if(action.charAt(rules.indexOf("3"))=='0'){
                 ud=check_action_step(3);
             }
         }
         if(rl>ud){
             if(rules.contains("2")){
                 act[rules.indexOf("2")]='1';
             }else if(rules.contains("3")){
                 act[rules.indexOf("3")]='1';
             }
         }else{
             if(rules.contains("0")){
                 act[rules.indexOf("0")]='1';
             }else if(rules.contains("1")){
                 act[rules.indexOf("1")]='1';
             }
         }
     }
     Actions=action;
     System.out.println(Rules+"\t\t"+Actions);
     action=String.copyValueOf(act);
      }

        return action;
    }
        else {
        String act = null;
        if (direction == 0) {
            Rules="230";
            act = check_action("230");
            Actions=act;
        } else if (direction == 1) {
            Rules="231";
            act = check_action(Rules);
            Actions=act;
        } else if (direction == 2) {
            Rules="012";
            act = check_action(Rules);
            Actions=act;
        } else if (direction == 3) {
            Rules="013";
            act = check_action(Rules);
            Actions=act;
        }
    return act;
    }
    }
public static int check_action_step(int direction){
        int count=1;
        int x=positions.get(0).getX_init();
        int y=positions.get(0).getY_init();
        for(int i=0;i<positions.size();i++){
            if(direction==0){
                x+=(20*count);
            }else if(direction==1){
                x-=(20*count);
            }else if(direction==2){
                y-=(20*count);
            }else if(direction==3){
                y+=(20*count);
            }
                if(x==positions.get(positions.size()-1-i).getX_init() && y==positions.get(positions.size()-1-i).getY_init()){
                    break;
                } else{
                    count++;
                }
        }
        return count;
}
}
/**Done by Sobrattee Mohammad Muzzammil**/
