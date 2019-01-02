//Commenting this out because it has a vsp playground dependency that does not resolve that well (via jitpack).  It also does not
//work that well: It is able to pick about four of the downtown lines, but not (the routes of) any of the other lines that are
//visible in the OSM viewers.
//
//Note that for the downtown lines that it picks up, one still needs to add departures manually to see anything in VIA.
//
//kai, dec'18'


///* *********************************************************************** *
// * project: org.matsim.*
// *                                                                         *
// * *********************************************************************** *
// *                                                                         *
// * copyright       : (C) 2012 by the members listed in the COPYING,        *
// *                   LICENSE and WARRANTY file.                            *
// * email           : info at matsim dot org                                *
// *                                                                         *
// * *********************************************************************** *
// *                                                                         *
// *   This program is free software; you can redistribute it and/or modify  *
// *   it under the terms of the GNU General Public License as published by  *
// *   the Free Software Foundation; either version 2 of the License, or     *
// *   (at your option) any later version.                                   *
// *   See also COPYING, LICENSE and WARRANTY file                           *
// *                                                                         *
// * *********************************************************************** */
//
//package br.joinville.inputDataPreparation.network;
//
//import org.apache.log4j.Logger;
//import org.matsim.api.core.v01.network.NetworkWriter;
//import org.matsim.core.config.ConfigUtils;
//import org.matsim.core.scenario.MutableScenario;
//import org.matsim.core.scenario.ScenarioUtils;
//import org.matsim.core.utils.geometry.CoordinateTransformation;
//import org.matsim.core.utils.geometry.transformations.TransformationFactory;
//import org.matsim.pt.transitSchedule.api.TransitScheduleWriter;
//import org.openstreetmap.osmosis.core.filter.common.IdTrackerType;
//import org.openstreetmap.osmosis.xml.common.CompressionMethod;
//import playground.vsp.andreas.mzilske.osm.JOSMTolerantFastXMLReader;
//import playground.vsp.andreas.mzilske.osm.NetworkSink;
//import playground.vsp.andreas.mzilske.osm.TransitNetworkSink;
//
//import java.io.File;
//
//public class RunNetworkAndTransitScheduleFromOSMGenerator{
//
//	private final static Logger log = Logger.getLogger( RunNetworkAndTransitScheduleFromOSMGenerator.class );
//
//	String inFile;
//	String fromCoordSystem;
//	String toCoordSystem;
//	String networkOutFile;
//	String transitScheduleOutFile;
//
//	public RunNetworkAndTransitScheduleFromOSMGenerator( String inFile, String fromCoordSystem, String toCoordSystem, String networkOutFile, String transitScheduleOutFile ){
//		this.inFile = inFile;
//		this.fromCoordSystem = fromCoordSystem;
//		this.toCoordSystem = toCoordSystem;
//		this.networkOutFile = networkOutFile;
//		this.transitScheduleOutFile = transitScheduleOutFile;
//	}
//
//	public static void main(String[] args){
////		final String inFile = "original-input-data/data-from-joinville-2018-11-23/AnaBazzanFiles/Network/joinville_filtrada.osm";
//		final String inFile = "original-input-data/network/2018-11-22-from-geofabrik/allroads.osm" ;
//		new RunNetworkAndTransitScheduleFromOSMGenerator( inFile,
//			  TransformationFactory.WGS84,
//			  "EPSG:32722", "transit-network.xml",
//			  "transitschedule.xml").convertOsm2Matsim( new String [] {"bus"} );
//	}
//
//	public void convertOsm2Matsim(){
//		convertOsm2Matsim(null);
//	}
//
//	public void convertOsm2Matsim(String[] transitFilter){
//
//		log.info("Start...");
//		MutableScenario scenario = (MutableScenario) ScenarioUtils.createScenario(ConfigUtils.createConfig());
//		scenario.getConfig().transit().setUseTransit(true);
//		JOSMTolerantFastXMLReader reader = new JOSMTolerantFastXMLReader(new File(inFile), false, CompressionMethod.None);
//
//		CoordinateTransformation coordinateTransformation = TransformationFactory.getCoordinateTransformation(this.fromCoordSystem, this.toCoordSystem);
//		NetworkSink networkGenerator = new NetworkSink(scenario.getNetwork(), coordinateTransformation);
//
//		// Anmerkung trunk, primary und secondary sollten in Bln als ein Typ behandelt werden
//
//		// Autobahn
//		networkGenerator.setHighwayDefaults(1, "motorway",      2,  100.0/3.6, 1.2, 2000, true); // 70
//		networkGenerator.setHighwayDefaults(1, "motorway_link", 1,  60.0/3.6, 1.2, 1500, true); // 60
//		// Pseudoautobahn
//		networkGenerator.setHighwayDefaults(2, "trunk",         2,  50.0/3.6, 0.5, 1000, false); // 45
//		networkGenerator.setHighwayDefaults(2, "trunk_link",    1,  50.0/3.6, 0.5, 1000, false); // 40
//		// Durchgangsstrassen
//		networkGenerator.setHighwayDefaults(3, "primary",       1,  50.0/3.6, 0.5, 1000, false); // 35
//		networkGenerator.setHighwayDefaults(3, "primary_link",  1,  50.0/3.6, 0.5, 1000, false); // 30
//
//		// Hauptstrassen
//		networkGenerator.setHighwayDefaults(4, "secondary",     1,  50.0/3.6, 0.5, 1000, false); // 30
//		// Weitere Hauptstrassen
//		networkGenerator.setHighwayDefaults(5, "tertiary",      1,  30.0/3.6, 0.8,  600, false); // 25
//		// bis hier ca wip
//
//		// Nebenstrassen
//		networkGenerator.setHighwayDefaults(6, "minor",         1,  30.0/3.6, 0.8,  600, false); // nix
//		// Alles Moegliche, vor allem Nebenstrassen auf dem Land, meist keine 30er Zone
//		networkGenerator.setHighwayDefaults(6, "unclassified",  1,  30.0/3.6, 0.8,  600, false);
//		// Nebenstrassen, meist 30er Zone
//		networkGenerator.setHighwayDefaults(6, "residential",   1,  30.0/3.6, 0.6,  600, false);
//		// Spielstrassen
//		networkGenerator.setHighwayDefaults(6, "living_street", 1,  15.0/3.6, 1.0,  300, false);
//
//		log.info("Reading " + this.inFile);
//		TransitNetworkSink transitNetworkSink = new TransitNetworkSink(scenario.getNetwork(), scenario.getTransitSchedule(), coordinateTransformation, IdTrackerType.Dynamic);
//		transitNetworkSink.setTransitModes(transitFilter);
//		reader.setSink(networkGenerator);
//		networkGenerator.setSink(transitNetworkSink);
//		reader.run();
//		log.info("Writing network to " + this.networkOutFile);
//		new NetworkWriter(scenario.getNetwork()).write(this.networkOutFile);
//		log.info("Writing transit schedule to " + this.transitScheduleOutFile);
//		new TransitScheduleWriter(scenario.getTransitSchedule()).writeFile(this.transitScheduleOutFile);
//		log.info("Done...");
//	}
//
//}
