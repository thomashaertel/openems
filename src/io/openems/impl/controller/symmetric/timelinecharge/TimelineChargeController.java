/*******************************************************************************
 * OpenEMS - Open Source Energy Management System
 * Copyright (c) 2016, 2017 FENECON GmbH and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *   FENECON GmbH - initial API and implementation and initial documentation
 *******************************************************************************/
package io.openems.impl.controller.symmetric.timelinecharge;

import java.util.List;

import io.openems.api.channel.ConfigChannel;
import io.openems.api.controller.Controller;
import io.openems.api.doc.ConfigInfo;
import io.openems.api.doc.ThingInfo;
import io.openems.api.exception.InvalidValueException;
import io.openems.impl.controller.supplybusswitch.Ess;

@ThingInfo(title = "Timeline charge (Symmetric)")
public class TimelineChargeController extends Controller {

	/*
	 * Constructors
	 */
	public TimelineChargeController() {
		super();
	}

	public TimelineChargeController(String thingId) {
		super(thingId);
	}

	/*
	 * Config
	 */
	@ConfigInfo(title = "Ess", description = "Sets the Ess devices.", type = Ess.class)
	public final ConfigChannel<List<Ess>> esss = new ConfigChannel<List<Ess>>("esss", this);

	@ConfigInfo(title = "Primary-Ess", description = "OpenEMS is supplied by this Ess. Will reserve some load.", type = Ess.class)
	public final ConfigChannel<Ess> primaryEss = new ConfigChannel<>("primaryEss", this);

	@ConfigInfo(title = "Grid-Meter", description = "Sets the grid meter.", type = Meter.class)
	public final ConfigChannel<Meter> meter = new ConfigChannel<>("meter", this);

	@ConfigInfo(title = "Max-ApparentPower", description = "How much apparent power the grid connection can take.", type = Long.class)
	public final ConfigChannel<Long> allowedApparent = new ConfigChannel<>("allowedApparent", this);

	@ConfigInfo(title = "Cos-Phi", description = "The cos-phi to hold on the grid.", type = Double.class)
	public final ConfigChannel<Double> cosPhi = new ConfigChannel<>("cosPhi", this);

	/*
	 * Methods
	 */
	@Override
	public void run() {
		try {
			long allowedApparentCharge = meter.value().apparentPower.value() - allowedApparent.value();
			long socSum = primaryEss.value().soc.value();
			long currentReactivePower = primaryEss.value().reactivePower.value();
			// causes faster charge for primaryEss
			if (primaryEss.value().soc.value() > 10) {
				socSum = primaryEss.value().soc.value() - 10;
			}
			for (Ess ess : esss.value()) {
				currentReactivePower += ess.reactivePower.value();
				socSum += ess.soc.value();
			}
			//
			double cosPhi = this.cosPhi.value();
			double phi = Math.acos(cosPhi);
			long q = (long) ((meter.value().activePower.value() * Math.tan(phi)) - meter.value().reactivePower.value())
					* -1;
			q += currentReactivePower;
			long p = 0L;
			// primaryEss.value().power
			// .setActivePower(ControllerUtils.calculateActivePowerFromApparentPower(apparentPower, cosPhi));
			for (Ess ess : esss.value()) {

			}
		} catch (InvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
