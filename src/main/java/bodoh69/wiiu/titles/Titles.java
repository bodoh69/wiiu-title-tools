package bodoh69.wiiu.titles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class Titles {
    private final Map<String, Title> idMap = new LinkedHashMap<>();

    private Titles() {
    }

    private void add(Title title) {
        idMap.putIfAbsent(title.getTitleId(), title);
    }

    public Title lookup(String titleId) {
        return idMap.get(titleId);
    }

    void addFromTabSeparatedUTF8(InputStream in)
            throws IOException {
        requireNonNull(in, "input stream");
        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.length() < 4)
                    continue;
                String[] values = line.split("\t");
                Title title = new Title(
                        values[0],
                        values[1],
                        values.length > 2 ? values[2] : "UNK",
                        values.length > 3 ? values[3] : "UNK");
                this.add(title);
            }
        }
    }
    public static Titles selfInit() throws IOException {
        try (InputStream in = Titles.class.getResourceAsStream("/system.tsv")) {
            Titles t = Titles.fromTabSeparatedUTF8(in);
            try (InputStream in2 =  Titles.class.getResourceAsStream("/titles.tsv")) {
                t.addFromTabSeparatedUTF8(in2);
            }
            return t;
        }
    }

    public static Titles fromTabSeparatedUTF8(InputStream in)
            throws IOException {
        requireNonNull(in, "input stream");
        Titles titles = new Titles();
        titles.addFromTabSeparatedUTF8(in);
        return titles;
    }
}
