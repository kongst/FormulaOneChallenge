
public class Car implements Comparable<Car> {

	public Car(int teamNumber, int totalTeam) {
		this.teamNumber = teamNumber;

		// Top speed = (150 + 10 * i)/1.8 meters per 2 second 
		this.topSpeed = (150 + 10 * teamNumber)/1.8;
		this.acceleration = 2 * teamNumber;
		this.handlingFactor = 0.8;
		this.currentSpeed = 0;
		this.raceTime = 0;
		this.usedNitro = false;

		// Calculate start position
		// The start line for (i + 1)th car is 200 * i meters behind the ith car
		this.currentDistance = 200 * (totalTeam - teamNumber);
	}

	//
	// Linear Motion Formulas:
	// s = v*t + 0.5*a*t^2
	// v = v + a*t
	//
	// Ref: http://www.engineeringtoolbox.com/motion-formulas-d_941.html
	//

	public void calculateDistance(double time) {
		double distance = currentSpeed * time + 0.5 * acceleration * (time * time);
		currentDistance += distance;
		raceTime += time;
	}

	public void calculateSpeed(double time) {
		currentSpeed = currentSpeed + acceleration * time;
	}

	public boolean hasNitro() {
		return (usedNitro == false);
	}
	public void useNitro() {
		if (usedNitro == false) {
			//System.out.println("use Nitro: " + getTeamNumber());
			
			currentSpeed = currentSpeed * 2;

			if (currentSpeed > topSpeed) {
				currentSpeed = topSpeed;
			}

			usedNitro = true;
		}
	}

	public void reduceSpeed() {
		//System.out.println("reduce speed: " + getTeamNumber());
		currentSpeed = handlingFactor * currentSpeed;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public int getTeamNumber() {
		return teamNumber;
	}

	public double getcurrentDistance() {
		return currentDistance;
	}
	
	public double getcurrentSpeed() {
		return currentSpeed;
	}
	
	public double getcurrentRaceTime() {
		return raceTime;
	}

	@Override
	public int compareTo(Car car) {
//		return (int) (this.currentDistance - car.currentDistance);
		
		return (int) (car.currentDistance - this.currentDistance);
	}

	private int teamNumber;
	private double topSpeed;
	private double acceleration;
	private double handlingFactor;
	private double currentSpeed;
	private double currentDistance;
	private double raceTime;
	private boolean usedNitro;
	private boolean isFinished;
}
