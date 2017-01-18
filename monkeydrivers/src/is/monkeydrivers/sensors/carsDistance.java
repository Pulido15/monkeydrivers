package is.monkeydrivers.sensors;

public class carsDistance {
    travelCar speedFirstCar;
    travelCar speedSecondCar;
    int finalSpeed;

    public carsDistance(travelCar FirstCar, travelCar SecondCar){
        speedFirstCar = FirstCar;
        speedSecondCar = SecondCar;
        finalSpeed = 0;
    }

    public double newSpeed(){
        return isTheSameSpeed() ? finalSpeed:
            isTheFirstCarMoreFaster() ? getSpeed(speedSecondCar) * 0.9 :
            getSpeed(speedSecondCar);

    }

    public boolean isTheSameSpeed() {
        return getSpeed(speedFirstCar) == getSpeed(speedSecondCar);
    }

    public static double getSpeed(travelCar car) {
        try{
            return car.getDistance()/ car.getTime();
        }catch (ArithmeticException e){
            return 0;
        }
    }

    public boolean isTheFirstCarMoreFaster() {
        return (getSpeed(speedFirstCar) > getSpeed(speedSecondCar));
    }
}
