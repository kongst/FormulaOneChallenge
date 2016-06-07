import java.util.ArrayList;
import java.util.Collections;

public class FormularOneCircuitController {

	public FormularOneCircuitController(int totalTeam, double trackLength) {
		this.totalTeam = totalTeam;
		this.trackLength = trackLength;
		this.reAssessmentTime = 2;

		for (int i = 1; i <= totalTeam; i++) {
			Car car = new Car(i, totalTeam);
			carList.add(car);
		}
	}

	public void startRace() {

		int numFinished = 0;

		while (numFinished < totalTeam) {		
			// Sorting car by distance here to not introduce a lot of loop in
			// checkDistanceToReduceSpeed() and useNitroForLastPosition()
			Collections.sort(carList);
			// dumpCar();

			// Reduce speed if there is any car within 10 meters.
			checkDistanceToReduceSpeed();

			// Use nitro for last position
			useNitroForLastPosition();
			
			// Calculate current distance and current speed after 2 second
			numFinished = calculateDistanceAndSpeed(numFinished);
		}
	}

	private int calculateDistanceAndSpeed(int numFinished) {
		for (int i = 0; i < carList.size(); i++) {
			Car car = carList.get(i);

			if (!car.isFinished()) {
				car.calculateDistance(reAssessmentTime);
				car.calculateSpeed(reAssessmentTime);

				if (car.getcurrentDistance() >= trackLength) {
					car.setFinished(true);
					numFinished++;

					System.out.println("Team " + car.getTeamNumber() + " is finished at speed " + car.getcurrentSpeed() + " in " + car.getcurrentRaceTime()  + " sec");
				}
			}
		}
		return numFinished;
	}

	private void checkDistanceToReduceSpeed() {
		// carList need to be sorting before calling this method

		// checks if there is any car within 10 meters of his car,
		// his speed reduces to: hf * (speed at that moment)
		for (int i = 0; i < carList.size(); i++) {
			Car car = carList.get(i);

			if (!car.isFinished()) {

				boolean bReducedSpeed = false;

				if (i > 0) {
					Car previousCar = carList.get(i - 1);

					double distance = car.getcurrentDistance() - previousCar.getcurrentDistance();
					if (Math.abs(distance) <= 10) {
						car.reduceSpeed();
						bReducedSpeed = true;
					}
				}

				if ((i < carList.size() - 1) && bReducedSpeed == false) {
					Car nextCar = carList.get(i + 1);

					if (!nextCar.isFinished()) {
						double distance = nextCar.getcurrentDistance() - car.getcurrentDistance();
						if (Math.abs(distance) <= 10) {
							car.reduceSpeed();
						}
					}
				}
			}
		}
	}

	private void useNitroForLastPosition() {
		// carList need to be sorting before calling this method
		Car carForNitro = carList.get(carList.size() - 1);
		if (carForNitro.hasNitro()) {
			carForNitro.useNitro();
		}
	}

	public void dumpCar() {

		System.out.println("----------------------");
		for (int i = 0; i < carList.size(); i++) {
			Car car = carList.get(i);
			System.out.println(
					car.getTeamNumber() + " = " + car.getcurrentDistance() + " (" + car.getcurrentSpeed() + ")");
		}
	}

	private int totalTeam;
	private double trackLength;
	private double reAssessmentTime;

	ArrayList<Car> carList = new ArrayList<Car>();

}
