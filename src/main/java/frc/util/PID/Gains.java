package frc.util.PID;

/**
 * This class have MP and PID values.
 */
public class Gains {
	public double kv, ka; // MP values

	public double kg = 0; // talonFX gravity value
	public double ks = 0; // talonFX static value

	public double kp, ki, kd, KiThreshold; // PID values

	public double Kf = 0;
	public int Ki_zone = 0;
	public String nameGains;

	/**
	 * This constractor not recommanded to use!
	 * <p>
	 * This constractor will set kp, ki, kd, KiThreshold, Kv and Ka to 0.
	 */
	public Gains(String _nameGains) {
		kp = 0;
		ki = 0;
		kd = 0;

		kv = 0;
		ka = 0;

		KiThreshold = 0;
		nameGains = _nameGains;
	}

	/**
	 * This constractor need to use if you whant to do PID command with subsystem.
	 * <p>
	 * This constractor will set KiThreshold, Kv and Ka to 0.
	 * <p>
	 * 
	 * @param kp_ - kp to use with this gains.
	 * @param ki_ - ki to use with this gains.
	 * @param kd_ - kd to use with this gains.
	 */
	public Gains(String _nameGains, double kp_, double ki_, double kd_) {
		kp = kp_;
		ki = ki_;
		kd = kd_;

		kv = 0;
		ka = 0;

		KiThreshold = 0;
		nameGains = _nameGains;
	}

	/**
	 * This constractor need to use if you whant to do PID command with subsystem.
	 * <p>
	 * This constractor will set Kv and Ka to 0.
	 * <p>
	 * 
	 * @param kp_          - kp to use with this gains.
	 * @param ki_          - ki to use with this gains.
	 * @param kd_          - kd to use with this gains.
	 * @param KiThreshold_ - KiThreshold to use with this gains.
	 */
	public Gains(String _nameGains, double kp_, double ki_, double kd_, double KiThreshold_) {
		kp = kp_;
		ki = ki_;
		kd = kd_;

		kv = 0;
		ka = 0;

		KiThreshold = KiThreshold_;
		nameGains = _nameGains;

	}

	/**
	 * This constractor need to use if you whant to do MP command with subsystem.
	 * <p>
	 * This constractor will set KiThreshold to 0.
	 * <p>
	 * 
	 * @param kv_ - kv to use with this gains.
	 * @param ka_ - ka to use with this gains.
	 * @param kp_ - kp to use with this gains.
	 * @param ki_ - ki to use with this gains.
	 * @param kd_ - kd to use with this gains.
	 */
	public Gains(String _nameGains, double kv_, double ka_, double kp_, double ki_, double kd_) {
		kp = kp_;
		ki = ki_;
		kd = kd_;

		kv = kv_;
		ka = ka_;

		KiThreshold = 0;
		nameGains = _nameGains;

	}

	public Gains(String _nameGains, double kv_, double ka_, double kp_, double ki_, double kd_, double kg_, double ks_) {
		kp = kp_;
		ki = ki_;
		kd = kd_;

		kv = kv_;
		ka = ka_;

		ks = ks_;
		kg = kg_;

		KiThreshold = 0;
		nameGains = _nameGains;

	}

	/**
	 * This constractor need to use if you whant to do MP command with subsystem.
	 * <p>
	 * This constractor will set KiThreshold to 0.
	 * <p>
	 * 
	 * @param kv_      - kv to use with this gains.
	 * @param ka_      - ka to use with this gains.
	 * @param kp_      - kp to use with this gains.
	 * @param ki_      - ki to use with this gains.
	 * @param kd_      - kd to use with this gains.
	 * @param Kf_      - kf to use with this gains.
	 * @param Ki_zone_ - ki_zone to use with this gains.
	 */
	public Gains(String _nameGains, double kv_, double ka_, double kp_, double ki_, double kd_, double Kf_,
			int Ki_zone_) {
		kp = kp_;
		ki = ki_;
		kd = kd_;

		kv = kv_;
		ka = ka_;

		Kf = Kf_;
		Ki_zone = Ki_zone_;

		KiThreshold = 0;
		nameGains = _nameGains;
	}
}
