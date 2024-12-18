package ai;

import game.PowerUp;
import game.Target;
import game.Tank;
import game.TankAIBase;
import game.Vec2;

public class TankAI extends TankAIBase {
    public  String getPlayerName() {
    return "Akhil";
    }

    public int getPlayerPeriod() {
    return 4;              
    }

    private double calculateDistance(Vec2 position1,Vec2 position2) {
        double dx = position1.x - position2.x;
        double dy = position1.y - position2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private Target getClosestTarget() {
        Target[] targets = getTargets();
        Target closestTarget = null;
        double closestDistance = Double.MAX_VALUE;
        for (int i =0; i < targets.length; i++) {
            double distance = calculateDistance(getTankPos(), targets[i].getPos());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestTarget = targets[i];
            }
        }
        return closestTarget;
    }


    //choose da best powerup based on the distance

    private PowerUp getClosestPowerUp() {
        PowerUp[] powerUps = getPowerUps();
        PowerUp closestPowerUp = null;
        double closestDistance = Double.MAX_VALUE;

        for (int i =0; i < powerUps.length; i++) {
            double distance = calculateDistance(getTankPos(), powerUps[i].getPos());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPowerUp = powerUps[i];
            }
        }
        return closestPowerUp;
    }


    
    public boolean updateAI() {
        double maxRange = getTankShotRange();
        PowerUp closestPowerUp = getClosestPowerUp();
        Target closestTarget = getClosestTarget();
        Vec2 tankPos = getTankPos();
    
        // go far the closest target
        if (closestTarget != null) {
            Vec2 targetPos = closestTarget.getPos();
            double distanceToTarget = calculateDistance(tankPos, targetPos);
    
            // Only shoot if the target is within the range
            if (distanceToTarget <= maxRange) {
                Vec2 shootDirection = new Vec2(targetPos.x - tankPos.x, targetPos.y - tankPos.y);
                queueCmd("shoot", shootDirection);
            }
        }
    
        // If the target is closer than the closest pwr up, prioritize it
        if (closestTarget != null) {
            Vec2 targetPos = closestTarget.getPos();
            double distanceToTarget = calculateDistance(tankPos, targetPos);
            double distanceToPowerUp = closestPowerUp != null ? calculateDistance(tankPos, closestPowerUp.getPos()) : Double.MAX_VALUE;
    
            // Go towards the target if it is closer than the pwr up
            if (distanceToTarget < distanceToPowerUp) {
                Vec2 targetMoveDirection = new Vec2(targetPos.x - tankPos.x, targetPos.y - tankPos.y);
                queueCmd("move", targetMoveDirection);
            }
            // If the power-up is closer, go towards the pwr up
            else if (closestPowerUp != null) {
                Vec2 powerUpPos = closestPowerUp.getPos();
                Vec2 moveDirection;
                // Move on x axis if the x diff is larger, or just move vertically
                if (Math.abs(powerUpPos.x - tankPos.x) > Math.abs(powerUpPos.y - tankPos.y)) {
                    moveDirection = new Vec2((powerUpPos.x - tankPos.x), 0);  // move horizontally
                } else {
                    moveDirection = new Vec2(0, (powerUpPos.y - tankPos.y));  // move vertically
                }
    
                queueCmd("move", moveDirection);
            }
            return true;
        }



        // go to the power up closest to u

        if (closestPowerUp != null) {
            Vec2 powerUpPos = closestPowerUp.getPos();
            Vec2 moveDirection;
            // Move on x-axis if the x diff is larger, or just move vertically
            if (Math.abs(powerUpPos.x - tankPos.x) > Math.abs(powerUpPos.y - tankPos.y)) {
                moveDirection = new Vec2((powerUpPos.x - tankPos.x),0);  // move horizontally
            } else {
                moveDirection = new Vec2(0, (powerUpPos.y - tankPos.y));  // move vertically
            }

            queueCmd("move", moveDirection);

        }
        return true;

    }

}

