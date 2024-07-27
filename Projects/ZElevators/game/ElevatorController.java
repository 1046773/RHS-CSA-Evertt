package game;

// Interface for recieving elevator requests.
//  Requests are generated by zombies, when they "press" the elevator's buttons.
//  Students should implement this interface.
public interface ElevatorController {
    // Enums
    public enum Direction {
        None,
        Up,
        Down
    }

    // Students should implement these function to return their name & period (note that returning -1 for the period will avoid posting to the leaderboard)
    public String getStudentName();
    public int getStudentPeriod();

    // Event: Game has started
    public void onGameStarted(Game game);

    // Event: "outside-the-elevator" request, requesting an elevator.
    //  The event will be triggered when a button is pressed AND when it is cleared (enableRequest indicates which).
    public void onElevatorRequestChanged(int floorIdx, Direction dir, boolean enableRequest); 

    // Event: "inside-the-elevator" request, requesting to go to a floor.
    //  The event will be triggered when a button is pressed AND  & when it is cleared (enableRequest indicates which).
    public void onFloorRequestChanged(int elevatorIdx, int floorIdx, boolean enableRequest);

    // Event: Elevator has arrived at the floor & doors are open.
    public void onElevatorArrivedAtFloor(int elevatorIdx, int floorIdx, Direction headingDirection);

    // Event: Called each frame of the simulation (i.e. called continuously)
    public void onUpdate(double deltaTime);

    // Command: Tell an elevator to go to a floor and open its doors, allowing zombies to enter/exit at that floor.
    //  Note that arriving at and leaving a floor will clear internal elevator requests (both internal and external)
    //  Students do not need to override this method, instead it should be called to control the elevator.
    public default boolean gotoFloor(int elevatorIdx, int floorIdx) {
        Elevator elevator = Simulation.get(Game.get().getActivePlayerIdx()).getElevator(elevatorIdx);
        if (elevator != null) {
            elevator.setTargetFloor(floorIdx);
            Game.get().awardPoints(elevator.getPlayerIdx(), Game.POINTS_ELEVATOR_COMMAND);
        }
        return true;
    }
}
