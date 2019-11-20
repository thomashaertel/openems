package io.openems.edge.battery.renaultzoe;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import io.openems.edge.battery.renaultzoe.BatteryState;

@ObjectClassDefinition( //
		name = "BMS RenaultZoe", //
		description = "Implements the Renault Zoe battery.")
@interface Config {
	
	@AttributeDefinition(name = "Component-ID", description = "Unique ID of this Component")
	String id() default "bms0";
	
	@AttributeDefinition(name = "Alias", description = "Human-readable name of this Component; defaults to Component-ID")
	String alias() default "";
	
	@AttributeDefinition(name = "Modbus Unit-ID", description = "The Unit-ID of the Modbus device.")
	int modbusUnitId() default 0;
	
	@AttributeDefinition(name = "Battery state", description = "Switches the battery into the given state, if default is used, battery state is set automatically")
	BatteryState batteryState() default BatteryState.DEFAULT;

	@AttributeDefinition(name = "Is enabled?", description = "Is this Component enabled?")
	boolean enabled() default true;

	@AttributeDefinition(name = "Modbus-ID", description = "ID of Modbus brige.")
	String modbus_id() default "modbus0";
	
	@AttributeDefinition(name = "Error Delay", description = "When an error occurs, system will remain the given time in error delay state")
	long errorDelay() default 600;
	
	@AttributeDefinition(name = "Max Start Time", description = "Max Time in seconds allowed for starting the system")
	int maxStartTime() default 30;
	
	@AttributeDefinition(name = "Max Start Attempts", description = "Sets the counter how many time the system should try to start")
	int maxStartAttempts() default 5;
	
	@AttributeDefinition(name = "Start Not Successful Delay Time", description = "Sets the delay time in seconds how long the system should be stopped if it was not able to start")
	int startUnsuccessfulDelay() default 3600;
	
	@AttributeDefinition(name = "Pending Tolerance", description = "time in seconds, that is waited if system status cannot be determinated e.g. in case of reading errors")
	int pendingTolerance() default 15;
	
	@AttributeDefinition(name = "Watchdog", description = "Watchdog timeout in seconds")
	int watchdog() default 60;

	@AttributeDefinition(name = "Capacity [Wh]", description = "The capacity of the Battery Rack.")
	int capacity() default 0;

	@AttributeDefinition(name = "Modbus target filter", description = "This is auto-generated by 'Modbus-ID'.")
	String Modbus_target() default "";

	String webconsole_configurationFactory_nameHint() default "BMS RenaultZoe [{id}]";
}
