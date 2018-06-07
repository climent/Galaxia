package org.templegalaxia;

import heronarts.lx.model.LXModel;
import heronarts.lx.output.*;
import heronarts.lx.studio.LXStudio;
import org.templegalaxia.model.Temple;
import org.templegalaxia.patterns.gerald.*;
import org.templegalaxia.patterns.matty.*;
import org.templegalaxia.patterns.testing.*;
import processing.core.PApplet;

import java.net.SocketException;
import java.net.UnknownHostException;


public class GalaxiaGui extends PApplet {
    // System configuration flags
    private static final boolean MULTITHREADED = true;
    private static final boolean RESIZEABLE = true;

    static public void main(String args[]) {
        PApplet.main(new String[]{"--present", GalaxiaGui.class.getName()});
    }

    public void settings() {
        size(displayWidth, displayHeight, P3D);
    }

    public BuildOutputs bo;
    public void setup() {
        startupInfo();

        // Load model
        LXModel model = new Temple(this);

        // Initialize LX
        LXStudio lx = new LXStudio(this, model, MULTITHREADED);

        // Configure UI
        lx.ui.setResizable(RESIZEABLE);
        lx.ui.preview.pointCloud.setPointSize(20);

        bo = new BuildOutputs(lx, model);

    }

    public void initialize(LXStudio lx, LXStudio.UI ui) {
        lx.registerPattern(MoveXPosition.class);
        lx.registerPattern(MoveYPosition.class);
        lx.registerPattern(MoveZPosition.class);
        lx.registerPattern(PetalIterator.class);
        lx.registerPattern(Teleport.class);
        lx.registerPattern(PetalChase.class);
        lx.registerPattern(Sparkle.class);
    }

    public void draw() {
        // Handled by LXStudio
    }

    private void startupInfo() {
        PApplet.println("Running sketch from ", sketchPath());
    }
}

class BuildOutputs {

    LXDatagramOutput output;
    StreamingACNDatagram datagram;

    BuildOutputs(LXStudio lx, LXModel model) {
        try {
            this.output = new LXDatagramOutput(lx);
            this.datagram = new StreamingACNDatagram(0, model);
            this.datagram.setAddress("192.168.0.50");

            this.output.addDatagram(this.datagram);

            lx.addOutput(this.output);

        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }
    }
}