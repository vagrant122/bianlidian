package com.bianlidian.desk.util;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import org.apache.log4j.Logger;

import com.bianlidian.desk.javafx.MainScene;
import com.bianlidian.desk.service.OrderService;

public class OrderAlerter extends Thread {
	private Logger log = Logger.getLogger(this.getClass());
	private Date lastTime = Calendar.getInstance().getTime();
	private MainScene mainScene;
	private Sequencer midi;
	private Sequence seq;

	public OrderAlerter(MainScene scene) {
		mainScene = scene;
		try {
			InputStream sound = OrderAlerter.class.getClassLoader()
			// .getResourceAsStream("resources/sound/sound.mid");
					.getResourceAsStream("sound/sound.mid");
			seq = MidiSystem.getSequence(sound);
			midi = MidiSystem.getSequencer();

			sound.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		mainScene.setAlerter(this);
	}

	@Override
	public void run() {
		if (log.isDebugEnabled()) {
			log.debug("OrderAlerter started!");
		}
		while (true) {
			try {
				Integer num = OrderService.hasNewOrder(lastTime,
						mainScene.getOrderStatus());
				if (num > 0) {
					mainScene.updateOrders();
				}
				num = OrderService.hasNewOrder(lastTime, OrderService.CREATED);
				if (num > 0) {
					if (log.isDebugEnabled()) {
						log.debug("OrderAlerter find " + num + " orders after "
								+ lastTime);
					}
					playBeep();
				}
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.SECOND, -1);
				lastTime = calendar.getTime();
				Thread.sleep(3 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void playBeep() {
		try {
			if (!midi.isOpen()) {
				midi.open();
				midi.setSequence(seq);

				midi.setLoopStartPoint(0);
				midi.setLoopEndPoint(-1);
				midi.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
				midi.start();
				if (log.isDebugEnabled()) {
					log.debug("play beep!");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void stopBeep() {
		try {
			if (midi.isOpen()) {
				midi.setLoopCount(0);
				midi.stop();
				midi.setMicrosecondPosition(0);
				midi.close();
				if (log.isDebugEnabled()) {
					log.debug("stop beep!");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
