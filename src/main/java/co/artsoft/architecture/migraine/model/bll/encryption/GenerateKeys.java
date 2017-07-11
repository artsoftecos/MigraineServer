package co.artsoft.architecture.migraine.model.bll.encryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service("generateKeys")
public class GenerateKeys {
	public void generateKeysFiles() throws Exception {
		Base64.Encoder encoder = Base64.getEncoder();

		PrivateKey pvt = loadPrivateKey();
		PublicKey kp = loadPublicKey();

		String outFile = "src/main/resources/Generated";
		Writer out = new FileWriter(outFile + ".key");
		out.write("-----BEGIN RSA PRIVATE KEY-----\n");
		out.write(encoder.encodeToString(pvt.getEncoded()));
		out.write("\n-----END RSA PRIVATE KEY-----\n");
		out.close();

		out = new FileWriter(outFile + ".pub");
		out.write("-----BEGIN RSA PUBLIC KEY-----\n");
		out.write(encoder.encodeToString(kp.getEncoded()));
		out.write("\n-----END RSA PUBLIC KEY-----\n");
		out.close();
	}

	public void generateKeys() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.generateKeyPair();
		Key pub = kp.getPublic();
		Key pvt = kp.getPrivate();

		String outFile = "src/main/resources/KeyGen";
		FileOutputStream out = new FileOutputStream(outFile + ".key");
		out.write(pvt.getEncoded());
		out.close();

		out = new FileOutputStream(outFile + ".pub");
		out.write(pub.getEncoded());
		out.close();

		System.err.println("Private key format: " + pvt.getFormat());
		// prints "Private key format: PKCS#8" on my machine

		System.err.println("Public key format: " + pub.getFormat());
		// prints "Public key format: X.509" on my machine

	}

	public PrivateKey loadPrivateKey() throws Exception {
		String outFile = "src/main/resources/KeyGen";
		/* Read all bytes from the private key file */
		Path path = Paths.get(outFile + ".key");
		byte[] bytes = Files.readAllBytes(path);

		/* Generate private key. */
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pvt = kf.generatePrivate(ks);
		return pvt;
	}

	public PublicKey loadPublicKey() throws Exception {
		String outFile = "src/main/resources/KeyGen";
		/* Read all the public key bytes */
		Path path = Paths.get(outFile + ".pub");
		byte[] bytes = Files.readAllBytes(path);

		/* Generate public key. */
		X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);

		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pub = kf.generatePublic(ks);

		return pub;
	}

	public void genDigSign() throws Exception {

		PrivateKey pvt = loadPrivateKey();

		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initSign(pvt);

		InputStream in = null;
		try {
			in = new FileInputStream("src/main/resources/GenFile");
			byte[] buf = new byte[2048];
			int len;
			while ((len = in.read(buf)) != -1) {
				sign.update(buf, 0, len);
			}
		} finally {
			if (in != null)
				in.close();
		}

		OutputStream out = null;
		try {
			out = new FileOutputStream("src/main/resources/EncryptFile");
			byte[] signature = sign.sign();
			out.write(signature);
		} finally {
			if (out != null)
				out.close();
		}
	}

	public void verDigSign() throws Exception {
		PublicKey pub = loadPublicKey();
		String dataFile = "src/main/resources/GenFile";
		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initVerify(pub);

		InputStream in = null;
		try {
			in = new FileInputStream(dataFile);
			byte[] buf = new byte[2048];
			int len;
			while ((len = in.read(buf)) != -1) {
				sign.update(buf, 0, len);
			}
		} finally {
			if (in != null)
				in.close();
		}

		/* Read the signature bytes from file */
		Path path = Paths.get("src/main/resources/EncryptFile");
		byte[] bytes = Files.readAllBytes(path);

		System.out.println(dataFile + ": Signature " + (sign.verify(bytes) ? "OK" : "Not OK"));
	}

}
