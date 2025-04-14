package views;

import domain.*;
import data.Persistencia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaAgregarAnimal extends JFrame {
    private JTextField txtEdad, txtPeso;
    private JComboBox<Especie> comboEspecies;
    private JComboBox<Sector> comboSectores;
    private JComboBox<Pais> comboPaises;

    public VentanaAgregarAnimal() {
        setTitle("Agregar Animal");
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Edad:"));
        txtEdad = new JTextField();
        add(txtEdad);

        add(new JLabel("Peso:"));
        txtPeso = new JTextField();
        add(txtPeso);

        add(new JLabel("Especie:"));
        comboEspecies = new JComboBox<>();
        for (Especie especie : Persistencia.getEspecies()) {
            comboEspecies.addItem(especie);
        }
        add(comboEspecies);

        add(new JLabel("Sector:"));
        comboSectores = new JComboBox<>();
        for (Sector sector : Persistencia.getSectores()) {
            comboSectores.addItem(sector);
        }
        add(comboSectores);

        add(new JLabel("País:"));
        comboPaises = new JComboBox<>();
        for (Pais pais : Persistencia.getPaises()) {
            comboPaises.addItem(pais);
        }
        add(comboPaises);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarAnimal();
            }
        });
        add(btnGuardar);

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void guardarAnimal() {
        try {
            int edad = Integer.parseInt(txtEdad.getText());
            double peso = Double.parseDouble(txtPeso.getText());
            Especie especie = (Especie) comboEspecies.getSelectedItem();
            Sector sector = (Sector) comboSectores.getSelectedItem();
            Pais pais = (Pais) comboPaises.getSelectedItem();

            Mamifero nuevoAnimal;
            if (especie.getTipoAlimentacion().esCarnivoro()) {
                nuevoAnimal = new Carnivoro(edad, peso, especie, sector, pais);
            } else {
                double valorFijo = 2.0; // valor fijo por defecto
                nuevoAnimal = new Herbivoro(edad, peso, especie, sector, valorFijo, pais);
            }

            Persistencia.getAnimales().add(nuevoAnimal);
            JOptionPane.showMessageDialog(this, "Animal agregado correctamente.");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar el animal: " + ex.getMessage());
        }
    }
}
