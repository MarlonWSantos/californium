/*******************************************************************************
 * Copyright (c) 2019 Bosch Software Innovations GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v20.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *    Achim Kraus (Bosch Software Innovations GmbH) - initial implementation.
 ******************************************************************************/
package org.eclipse.californium.interoperability.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.californium.scandium.dtls.cipher.CipherSuite;

/**
 * Test for openssl interoperability.
 * 
 * Requires external openssl installation, otherwise the tests are skipped. On
 * linux install just the openssl package (version 1.1.1). On windows you may
 * install git for windows, <a href="https://git-scm.com/download/win">git</a>
 * and add the extra tools to your path ("Git/mingw64/bin", may also be done
 * using a installation option). Alternatively you may install openssl for
 * windows on it's own <a href=
 * "https://bintray.com/vszakats/generic/download_file?file_path=openssl-1.1.1c-win64-mingw.zip">OpenSsl
 * for Windows</a> and add that to your path.
 * 
 * Note: the windows version 1.1.1a to 1.1.1c of the openssl s_server seems to
 * be broken. It starts only to accept, when the first message is entered.
 * Therefore the test are skipped on windows.
 */
public class OpenSslProcessUtil extends ProcessUtil {
	public enum AuthenticationMode {
		CERTIFICATE, CHAIN, TRUST
	}

	/**
	 * Create instance.
	 */
	public OpenSslProcessUtil() {
	}

	public String startupClient(CipherSuite cipher, AuthenticationMode authMode)
			throws IOException, InterruptedException {
		String openSslCipher = OpenSslUtil.CIPHERSUITES_MAP.get(cipher);
		if (cipher.isPskBased()) {
			startupPskClient(openSslCipher);
		} else {
			startupEcdsaClient(openSslCipher, authMode);
		}
		return openSslCipher;
	}

	public void startupPskClient(String ciphers) throws IOException, InterruptedException {
		execute("openssl", "s_client", "-dtls1_2", "-4", "-connect", "127.0.0.1:" + ScandiumUtil.PORT, "-no-CAfile",
				"-cipher", ciphers, "-curves", "prime256v1", "-psk", "73656372657450534b");
	}

	public void startupEcdsaClient(String ciphers, AuthenticationMode authMode)
			throws IOException, InterruptedException {
		List<String> args = new ArrayList<String>();
		args.addAll(Arrays.asList("openssl", "s_client", "-dtls1_2", "-4", "-connect", "127.0.0.1:" + ScandiumUtil.PORT,
				"-cipher", ciphers, "-curves", "prime256v1", "-cert", "client.pem"));
		startupEcdsa(args, authMode);
	}

	public String startupServer(CipherSuite cipher, AuthenticationMode authMode)
			throws IOException, InterruptedException {
		String openSslCipher = OpenSslUtil.CIPHERSUITES_MAP.get(cipher);
		if (cipher.isPskBased()) {
			startupPskServer(openSslCipher);
		} else {
			startupEcdsaServer(openSslCipher, authMode);
		}
		return openSslCipher;
	}

	public void startupPskServer(String ciphers) throws IOException, InterruptedException {
		execute("openssl", "s_server", "-4", "-dtls1_2", "-accept", "127.0.0.1:" + ScandiumUtil.PORT, "-listen",
				"-no-CAfile", "-cipher", ciphers, "-psk", "73656372657450534b");
	}

	public void startupEcdsaServer(String ciphers, AuthenticationMode authMode)
			throws IOException, InterruptedException {
		List<String> args = new ArrayList<String>();
		args.addAll(Arrays.asList("openssl", "s_server", "-4", "-dtls1_2", "-accept", "127.0.0.1:" + ScandiumUtil.PORT,
				"-listen", "-verify", "5", "-cipher", ciphers, "-cert", "server.pem"));
		startupEcdsa(args, authMode);
	}

	public void startupEcdsa(List<String> args, AuthenticationMode authMode) throws IOException, InterruptedException {
		switch (authMode) {
		case CERTIFICATE:
			args.add("-no-CAfile");
			break;
		case CHAIN:
			args.add("-no-CAfile");
			args.add("-cert_chain");
			args.add("caTrustStore.pem");
			break;
		case TRUST:
			args.add("-CAfile");
			args.add("trustStore.pem");
			break;
		}
		execute(args);
	}

	public void stop(long timeoutMillis) throws InterruptedException, IOException {
		sendln("Q");
		waitResult(timeoutMillis);
	}

}
