package com.bianlidian.desk.util;

import java.io.InputStream;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import com.bianlidian.desk.javafx.MainScene;

public class OrderAlerter extends Thread {
	private Logger log = Logger.getLogger(this.getClass());
	private MainScene mainScene;
	private Sequencer midi;
	private Sequence seq;

	public OrderAlerter(MainScene scene) {
		mainScene = scene;
		try {
			InputStream sound = OrderAlerter.class.getClassLoader()
					.getResourceAsStream("resources/sound/sound.mid");
			// .getResourceAsStream("sound/sound.mid");
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
		ConnectionFactory cf = new CachingConnectionFactory("121.199.60.251");
		// set up the listener and container
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(
				cf);
		Object listener = new Object() {
			public void handleMessage(String message) {
				System.out.println(message);
				playBeep();
			}
		};
		MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
		container.setMessageListener(adapter);
		container.setQueueNames("myQueue");
		container.start();

		if (log.isDebugEnabled()) {
			log.debug("OrderAlerter started!");
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

	public static void main(String[] args) {
		OrderAlerter alerter = new OrderAlerter(new MainScene());
		alerter.start();
	}
}
