package com.tjf.sample.github.apicontext.exception;

public class JediNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -52548244535504794L;

	private final int jediId;

	public JediNotFoundException(int jediId) {
		this.jediId = jediId;
	}

	public int getJediId() {
		return jediId;
	}

}
