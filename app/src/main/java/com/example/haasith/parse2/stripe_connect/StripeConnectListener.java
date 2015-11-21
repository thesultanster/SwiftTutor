package com.example.haasith.parse2.stripe_connect;

public interface StripeConnectListener {
	
	public abstract void onConnected();
	
	public abstract void onDisconnected();

	public abstract void onError(String error);
	
}