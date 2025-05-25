import java.sql.*;

public class HurtowniaKlockowApplication {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://vl36\\MSQLSERVER:1433;databaseName=KLOCKI_36";
        String user = "sa";
        String password = "HasloBazyDanych!";

        Connection conn = null;
        Connection conn2 = null;
        Connection conn3 = null;
        Connection conn4 = null;

        try {
            conn = DriverManager.getConnection(url, user, password);

            // 1. Rozpocznij transakcję (wyłącz auto-commit)
            conn.setAutoCommit(false);


            String sql1 = "with q1 as (\n" +
                    "select min(stan_magazynu) as minimalna_ilosc from HURTOWNIA_KLOCKOW)\n" +
                    "delete from HURTOWNIA_KLOCKOW\n" +
                    "where stan_magazynu = (select minimalna_ilosc from q1)";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1);) {


                int rowsDeleted = stmt1.executeUpdate();

                if(rowsDeleted == 0){
                    System.out.println("Nie istnieja jeszcze zadne");
                }
            }

            // 3. Wykonaj SELECT z orders
            String selectOrders = "SELECT * FROM HURTOWNIA_KLOCKOW";
            try (PreparedStatement stmtOrders = conn.prepareStatement(selectOrders);
                 ResultSet rsOrders = stmtOrders.executeQuery()) {

                int columnCount = rsOrders.getMetaData().getColumnCount();
                System.out.println("Tabela po transakcji nr 1");
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsOrders.getMetaData().getColumnName(i) + "\t");
                }
                System.out.println();
                while (rsOrders.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        String value = rsOrders.getString(i);
                        System.out.print((value != null ? value : "NULL") + "\t");
                    }
                    System.out.println();
                }
            }


            // 5. Zatwierdź transakcję
            conn.commit();
            System.out.println("\nTransakcja zatwierdzona.");

        } catch (SQLException e) {
            // W razie błędu wycofaj całą transakcję
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transakcja wycofana!");
                } catch (SQLException ex) {
                    System.err.println("Błąd podczas rollback: " + ex.getMessage());
                }
            }
            System.err.println("Błąd: " + e.getMessage());
        } finally {
            // Zamknij połączenie
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Błąd przy zamykaniu połączenia: " + e.getMessage());
                }
            }
        }

        try {
            conn2 = DriverManager.getConnection(url, user, password);

            // 1. Rozpocznij transakcję (wyłącz auto-commit)
            conn2.setAutoCommit(false);


            String sql1 = "insert into HURTOWNIA_KLOCKOW (tytul, opis, data_wydania, cena_jednostkowa, stan_magazynu, wartosc_zapasow)\n" +
                    "values('tytul' ,'opis','20051111', 10, 110, 1100),\n" +
                    "('tytul' ,'opis','20051111', 10, 120, 1200),\n" +
                    "('tytul' ,'opis','20051111', 10, 130, 1300),\n" +
                    "('tytul' ,'opis','20051111', 10, 140, 1400)";
            try (PreparedStatement stmt1 = conn2.prepareStatement(sql1);) {


                int rowsInserted = stmt1.executeUpdate();
            }

            // 3. Wykonaj SELECT z orders
            String selectOrders = "SELECT * FROM HURTOWNIA_KLOCKOW";
            try (PreparedStatement stmtOrders = conn2.prepareStatement(selectOrders);
                 ResultSet rsOrders = stmtOrders.executeQuery()) {

                int columnCount = rsOrders.getMetaData().getColumnCount();
                System.out.println("Tabela po transakcji nr 2");
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsOrders.getMetaData().getColumnName(i) + "\t");
                }
                System.out.println();
                while (rsOrders.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        String value = rsOrders.getString(i);
                        System.out.print((value != null ? value : "NULL") + "\t");
                    }
                    System.out.println();
                }
            }


            // 5. Zatwierdź transakcję
            conn2.commit();
            System.out.println("\nTransakcja zatwierdzona.");

        } catch (SQLException e) {
            // W razie błędu wycofaj całą transakcję
            if (conn2 != null) {
                try {
                    conn2.rollback();
                    System.err.println("Transakcja wycofana!");
                } catch (SQLException ex) {
                    System.err.println("Błąd podczas rollback: " + ex.getMessage());
                }
            }
            System.err.println("Błąd: " + e.getMessage());
        } finally {
            // Zamknij połączenie
            if (conn2 != null) {
                try {
                    conn2.setAutoCommit(true);
                    conn2.close();
                } catch (SQLException e) {
                    System.err.println("Błąd przy zamykaniu połączenia: " + e.getMessage());
                }
            }
        }
        try {
            conn3 = DriverManager.getConnection(url, user, password);

            // 1. Rozpocznij transakcję (wyłącz auto-commit)
            conn3.setAutoCommit(false);


            String sql1 = "update HURTOWNIA_KLOCKOW\n" +
                    "set stan_magazynu = round(stan_magazynu * 2, 0),\n" +
                    "\twartosc_zapasow = wartosc_zapasow * 2;\n" +
                    "insert into HURTOWNIA_KLOCKOW (tytul, opis, data_wydania, cena_jednostkowa, stan_magazynu, wartosc_zapasow)\n" +
                    "values('tytul' ,'opis','20051111', 200, 100, 20000);";
            try (PreparedStatement stmt1 = conn3.prepareStatement(sql1);) {


                int rowsInserted = stmt1.executeUpdate();
            }

            // 3. Wykonaj SELECT z orders
            String selectOrders = "SELECT * FROM HURTOWNIA_KLOCKOW";
            try (PreparedStatement stmtOrders = conn3.prepareStatement(selectOrders);
                 ResultSet rsOrders = stmtOrders.executeQuery()) {

                int columnCount = rsOrders.getMetaData().getColumnCount();
                System.out.println("Tabela po transakcji nr 3");
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsOrders.getMetaData().getColumnName(i) + "\t");
                }
                System.out.println();
                while (rsOrders.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        String value = rsOrders.getString(i);
                        System.out.print((value != null ? value : "NULL") + "\t");
                    }
                    System.out.println();
                }
            }


            // 5. Zatwierdź transakcję
            conn3.commit();
            System.out.println("\nTransakcja zatwierdzona.");

        } catch (SQLException e) {
            // W razie błędu wycofaj całą transakcję
            if (conn3 != null) {
                try {
                    conn3.rollback();
                    System.err.println("Transakcja wycofana!");
                } catch (SQLException ex) {
                    System.err.println("Błąd podczas rollback: " + ex.getMessage());
                }
            }
            System.err.println("Błąd: " + e.getMessage());
        } finally {
            // Zamknij połączenie
            if (conn3 != null) {
                try {
                    conn3.setAutoCommit(true);
                    conn3.close();
                } catch (SQLException e) {
                    System.err.println("Błąd przy zamykaniu połączenia: " + e.getMessage());
                }
            }
        }
        try {
            conn4 = DriverManager.getConnection(url, user, password);

            // 1. Rozpocznij transakcję (wyłącz auto-commit)
            conn4.setAutoCommit(false);

            for (int i = 0; i < 10; i++) {
                String sql1 = "insert into HURTOWNIA_KLOCKOW (tytul, opis, data_wydania, cena_jednostkowa, stan_magazynu, wartosc_zapasow)\n" +
                        "values('tytul' ,'opis','20051111', 10, 110, 1100)";

                try (PreparedStatement stmt1 = conn4.prepareStatement(sql1);) {
                    int rowsInserted = stmt1.executeUpdate();
                }
            }


            // 3. Wykonaj SELECT z orders
            String selectOrders = "SELECT * FROM HURTOWNIA_KLOCKOW";
            try (PreparedStatement stmtOrders = conn4.prepareStatement(selectOrders);
                 ResultSet rsOrders = stmtOrders.executeQuery()) {

                int columnCount = rsOrders.getMetaData().getColumnCount();
                System.out.println("Tabela po transakcji nr 4");
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsOrders.getMetaData().getColumnName(i) + "\t");
                }
                System.out.println();
                while (rsOrders.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        String value = rsOrders.getString(i);
                        System.out.print((value != null ? value : "NULL") + "\t");
                    }
                    System.out.println();
                }
            }


            // 5. Zatwierdź transakcję
            conn4.commit();
            System.out.println("\nTransakcja zatwierdzona.");

        } catch (SQLException e) {
            // W razie błędu wycofaj całą transakcję
            if (conn4 != null) {
                try {
                    conn4.rollback();
                    System.err.println("Transakcja wycofana!");
                } catch (SQLException ex) {
                    System.err.println("Błąd podczas rollback: " + ex.getMessage());
                }
            }
            System.err.println("Błąd: " + e.getMessage());
        } finally {
            // Zamknij połączenie
            if (conn4 != null) {
                try {
                    conn4.setAutoCommit(true);
                    conn4.close();
                } catch (SQLException e) {
                    System.err.println("Błąd przy zamykaniu połączenia: " + e.getMessage());
                }
            }
        }
    }
}