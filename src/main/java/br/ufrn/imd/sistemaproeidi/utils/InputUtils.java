package br.ufrn.imd.sistemaproeidi.utils;

import br.ufrn.imd.sistemaproeidi.model.enums.Genero;
import br.ufrn.imd.sistemaproeidi.model.enums.Horario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputUtils {
    private static Scanner scanner = new Scanner(System.in);

    public static String validarNome(String nome){
        if(nome.isBlank() || nome.matches(".*\\d.*")){
            System.out.println("Nome inválido");
            return null;
        }
        return nome;
    }

    public static String validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email inválido: o campo está vazio.");
            return null;
        }

        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex == email.length() - 1) {
            System.out.println("Email inválido: deve conter '@' e ele não pode estar no início ou no final.");
            return null;
        }

        if (email.indexOf('@', atIndex + 1) != -1) {
            System.out.println("Email inválido: deve conter apenas um '@'.");
            return null;
        }

        String dominio = email.substring(atIndex + 1);
        if (!dominio.contains(".") || dominio.startsWith(".") || dominio.endsWith(".")) {
            System.out.println("Email inválido: o domínio deve conter pelo menos um ponto e não pode iniciar ou terminar com ele.");
            return null;
        }
        return email;
    }


    public static String validarCPF(String cpf){
        cpf = cpf.replaceAll("[^\\d]", "");

        if(cpf.isBlank() || cpf.length() < 11){
            System.out.println("CPF inválido");
            return null;
        }

        return cpf;
    }

    public static String validarHorario(Horario horario){
        if(horario == Horario.OITO_E_TRINTA){
            return "8h30";
        }else{
            return "10h30";
        }
    }

    public static String validarTelefone(String numero){
        String novoNumero = numero.replaceAll("[^\\d]", "");
        if(novoNumero.isBlank() || novoNumero.length() < 11){
            System.out.println("Número de telefone inválido");
            return null;
        }
        return numero;

    }

    public static String formatLocalDate(LocalDate input){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return formatter.format(input);
    }

    public static LocalDate validarData(String input){
        LocalDate dataNascimento = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            dataNascimento = LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida");
        }
        return dataNascimento;
    }

    public static String formatEnum(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        input = (input.replace("_", " ")).toLowerCase();

        String[] words = input.toLowerCase().split(" ");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return capitalized.toString().trim();
    }

    public static LocalDate lerData(String tipoData){
        while (true) {
            try {
                System.out.println("Data de " + tipoData + " (yyyy-mm-dd): ");
                String input = scanner.nextLine();
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido! Por favor, insira a data no formato yyyy-mm-dd.");
            }
        }
    }

    public static <T extends Enum<T>> T lerEnum(Class<T> enumClass) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Opções disponíveis: ");
                for (T value : enumClass.getEnumConstants()) {
                    System.out.print(value.name() + " ");
                }
                System.out.println();

                System.out.print("Escolha uma opção: ");
                String entrada = scanner.nextLine().toUpperCase();

                return Enum.valueOf(enumClass, entrada);
            } catch (IllegalArgumentException e) {
                System.out.println("Entrada inválida! Por favor, insira um valor válido.");
            }
        }
    }

    public static Boolean lerBool(String mensagem){
        System.out.println(mensagem);
        while (true) {
            String entrada = scanner.nextLine().trim().toUpperCase();
            if (("SIM").contains(entrada)) {
                return true;
            } else if (("NÃO").contains(entrada) || ("NAO").contains(entrada) ) {
                return false;
            }
            System.out.println("Resposta inválida! Por favor, digite 'SIM' ou 'NÃO'.");
        }
    }

    public static String lerString(String mensagem){
        System.out.println(mensagem);

        while(true){
            String entrada = scanner.nextLine().trim();
            if(!entrada.isBlank()){
                return entrada;
            }
            System.out.println("Entrada inválida! Por favor, insira um valor não vazio.");
        }
    }

    public static Integer lerInteger(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, insira um número inteiro.");
            }
        }
    }


}
