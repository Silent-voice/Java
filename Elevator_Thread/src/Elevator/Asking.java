package Elevator;

import java.sql.Time;

enum EntryState{
    FR, ER
}

public class Asking {
    private EntryState m_entryState = EntryState.FR;
    private ElevatorState m_elevatorState = ElevatorState.UP;
    private int m_askingFloorNumber;
    private int m_askingElevator;
    private long m_askingTime;
    //private double m_actuallyStartTime = 0;//实际出发时间
    //private boolean m_ifCanAddInQueue;
    //private int m_currentTime;

    public Asking(){

    }

    public Asking(String input){
        String[] strings = input.split("[(,)]");
        //System.out.println(strings[1]);
        if(strings[1].equals("FR")) {
            m_entryState = EntryState.FR;
            m_askingFloorNumber = Integer.parseInt(strings[2]);
            if (strings[3].equals("UP"))
                m_elevatorState = ElevatorState.UP;
            else
                m_elevatorState = ElevatorState.DOWN;
            m_askingTime = (System.currentTimeMillis() - Floor.startTime) / 100;
        }
        else {
            m_entryState = EntryState.ER;
            m_askingElevator = Integer.parseInt(strings[2].replace("#", ""));
            m_askingFloorNumber = Integer.parseInt(strings[3]);
            m_askingTime = (System.currentTimeMillis() - Floor.startTime) / 100;
        }
    }

    public EntryState getM_entryState(){
        return m_entryState;
    }

    public int getM_askingFloorNumber(){
        return  m_askingFloorNumber;
    }

    public ElevatorState getM_elevatorState(){
        return m_elevatorState;
    }

    public long getM_askingTime(){
        return m_askingTime;
    }

    public int getM_askingElevator(){
        return m_askingElevator;
    }

}

