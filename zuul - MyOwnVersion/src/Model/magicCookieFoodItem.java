package Model;
public class magicCookieFoodItem extends FoodItem {
	private double carryWeightIncreaseInKg; // Players maximum carry weight (in kg.) will increase with this amount.

	/**
	 * 
	 * @param weight
	 * @param description
	 * @param name
	 * @param canBePickedUp
	 * @param carryWeightIncreaseInKg Players maximum carry weight (in kg.) will
	 *                            increase with this amount.
	 */
	public magicCookieFoodItem(double weight, String description, String name, boolean canBePickedUp, double carryWeightIncreaseInKg) {
		super(weight, description, name, canBePickedUp);
		this.carryWeightIncreaseInKg = carryWeightIncreaseInKg;
	}

	@Override
	public String getDescriptionAndAttributes() {
		return super.getDescriptionAndAttributes() + "\t\tMaximum weight increase: " + carryWeightIncreaseInKg + super.getUnitString(carryWeightIncreaseInKg);
	}

	public double getCarryWeightIncreaseInKg() {
		return carryWeightIncreaseInKg;
	}
	
	
}