package info.ponciano.lab.pitools.crypto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Steganos {

	private Map<String, String> dico;

	public Steganos() {
		this.dico = new HashMap<>();
		this.dico.put("A", "Dans les cieux");
		this.dico.put("B", "À tout jamais");
		this.dico.put("C", "Un monde sans fin");
		this.dico.put("D", "En une infinité");
		this.dico.put("E", "À perpétuité");
		this.dico.put("F", "Sempiternel");
		this.dico.put("G", "Durable");
		this.dico.put("H", "Sans cesse");
		this.dico.put("I", "Irrévocablement");
		this.dico.put("J", "Définitivement");
		this.dico.put("K", "Éternellement");
		this.dico.put("L", "Dans la gloire");
		this.dico.put("M", "Dans la lumière ");
		this.dico.put("N", "En paradis");
		this.dico.put("O", "Toujours");
		this.dico.put("P", "Dans la divinité");
		this.dico.put("Q", "Dans la déité");
		this.dico.put("R", "Dans la félicité");
		this.dico.put("S", "Dans son règne");
		this.dico.put("T", "Dans son royaume");
		this.dico.put("U", "Dans la béatitude");
		this.dico.put("V", "Dans la paix");
		this.dico.put("W", "Dans la extase");
		this.dico.put("X", "Dans la magnificence");
		this.dico.put("Y", "Au trône");
		this.dico.put("Z", "En toute éternité ");
	}

	public String cipher(String txt) {
		String results = "";
		for (int i = 0; i < txt.length(); i++) {
			Character c = txt.charAt(i);
			results += this.dico.get(c.toString().toUpperCase()) + "\n";
		}
		return results;
	}

	public static void main(String[] args) {
		String txt = "rimbaud";
		Steganos steganos = new Steganos();
		String results = steganos.cipher(txt);
		System.out.println(results);
		String ciphered = "Dans la félicité, dieu est \n" + "Irrévocablement\n" + "Dans la lumière et\n"
				+ "À tout jamais\n" + "Dans les cieux il vit\n" + "Dans la béatitude et demeure\n"
				+ "En une infinité d’êtres.";
		results = steganos.uncipher(ciphered);
		System.out.println(results);
	}

	private String uncipher(String ciphered) {
		StringBuilder results = new StringBuilder();
		List<String> lines = Arrays.stream(ciphered.split("\\n")).toList();
		for (String string : lines) {
			// test if it contains a key
			this.dico.forEach((k, v) -> {
				if (string.contains(v))
					results.append(k);

			});
		}
		return results.toString();
	}
}
