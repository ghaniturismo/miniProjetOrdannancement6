package application;

import algorithm.Solver;
import algorithm.approximate.ApproximateOpenShopCMax;
import algorithm.brute.BruteOpenShop;
import algorithm.genetic.GeneticOpenShopCMax;
import algorithm.genetic.core.crossover.CrossoverManager;
import algorithm.genetic.core.crossover.selection.ParentingManager;
import algorithm.genetic.core.makespan.MakespanManager;
import algorithm.genetic.core.mutation.MutationManager;
import algorithm.genetic.core.selection.SelectionManager;
import problem.Problem;
import problem.Schedule;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.Scanner;

public class IHM extends JFrame {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JTextArea inputArea = new JTextArea();

	    protected JLabel img = new JLabel();

	    protected JRadioButton approxButton = new JRadioButton("Methode d'Approximation");
	    protected JRadioButton geneticButton = new JRadioButton("Algorithme Genetique");
	    protected JRadioButton bruteButton = new JRadioButton("Methode Brute");

	    protected JTextField sizeOfPopulationF = new JTextField("20");
	    protected JTextField mutationF = new JTextField("0.05");
	    protected JTextField iterationsF = new JTextField("100");

	    protected JRadioButton simple = new JRadioButton("Ordonnancement Simple");
	    protected JRadioButton modified = new JRadioButton("Ordonnancement optimise");

	    protected JPanel geneticParamsPanel = new JPanel();

	    protected JTextArea infoArea = new JTextArea();

	    protected Schedule curSolution;

	    protected int bruteLimitOnOperations = 10;

	    public IHM () {
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(new Dimension(800, 600));
	        setLayout(new BorderLayout(10, 10));

	        JPanel mainPanel = new JPanel();
	        mainPanel.setLayout(new BorderLayout(10, 10));
	        add(mainPanel, BorderLayout.CENTER);

	        mainPanel.add(new JLabel("  Parametres d'entrees:  "), BorderLayout.NORTH);

	        JPanel inputPanel = new JPanel();
	        inputPanel.setLayout(new BorderLayout(10, 10));
	        mainPanel.add(inputPanel, BorderLayout.CENTER);

	        inputArea.setPreferredSize(new Dimension(400, 150));
	        inputArea.setEditable(true);
	        inputArea.getFont().deriveFont(Font.ITALIC);
	        inputArea.setForeground(Color.gray);
	        String param = "#Job #Machine\n" +
	                "tpsExecJob1Machine1 tpsExecJob1Machine2\n" +
	                "tpsExecJob2Machine1 tpsExecJob2Machine2";
	        inputArea.setText(param);


	        inputArea.addMouseListener(new MouseListener() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
					if(inputArea.getText().equals(param)) {
						inputArea.setText("");
						inputArea.getFont().deriveFont(Font.PLAIN);
						inputArea.setForeground(Color.black);
					}
	            }

	            @Override
	            public void mousePressed(MouseEvent e) {

	            }

	            @Override
	            public void mouseReleased(MouseEvent e) {

	            }

	            @Override
	            public void mouseEntered(MouseEvent e) {

	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                if(inputArea.getText().equals("")){
	                    inputArea.getFont().deriveFont(Font.ITALIC);
	                    inputArea.setForeground(Color.gray);
	                    inputArea.setText(param);
	                }
	            }
	        });
	        inputPanel.add(inputArea, BorderLayout.NORTH);

	        approxButton.setSelected(true);

	        ButtonGroup selectAlg = new ButtonGroup();
	        selectAlg.add(bruteButton);
	        selectAlg.add(approxButton);
	        selectAlg.add(geneticButton);

	        JPanel butAlgPanel = new JPanel();
	        butAlgPanel.setLayout(new BorderLayout());
	        JPanel selectAlgPanel = new JPanel();
	        selectAlgPanel.add(bruteButton);
	        selectAlgPanel.add(approxButton);
	        selectAlgPanel.add(geneticButton);
	        butAlgPanel.add(selectAlgPanel, BorderLayout.NORTH);

	        geneticParamsPanel.setLayout(new GridLayout(4,1));
	        butAlgPanel.add(geneticParamsPanel, BorderLayout.CENTER);

	        JPanel sop = new JPanel();
	        sop.add(new JLabel("Taille de la population: "));
	        sizeOfPopulationF.setPreferredSize(new Dimension(50, 20));
	        sop.add(sizeOfPopulationF);
	        geneticParamsPanel.add(sop);

	        sop = new JPanel();
	        sop.add(new JLabel("Probabilite de mutation: "));
	        mutationF.setPreferredSize(new Dimension(50, 20));
	        sop.add(mutationF);
	        geneticParamsPanel.add(sop);

	        sop = new JPanel();
	        sop.add(new JLabel("Nombre d'iterations: "));
	        iterationsF.setPreferredSize(new Dimension(50, 20));
	        sop.add(iterationsF);
	        geneticParamsPanel.add(sop);
	        geneticParamsPanel.setVisible(false);

	        sop = new JPanel();
	        sop.setLayout(new FlowLayout());
	        ButtonGroup s1 = new ButtonGroup();
	        simple.setSelected(true);
	        sop.add(simple);
	        s1.add(simple);
	        sop.add(modified);
	        s1.add(modified);
	        geneticParamsPanel.add(sop);

	        inputPanel.add(butAlgPanel,
	                BorderLayout.CENTER);

	        JButton apply = new JButton("Lancer");
	        apply.setPreferredSize(new Dimension(150, 30));
	        apply.addActionListener(new ApplyButtonListener());
	        inputPanel.add(apply, BorderLayout.SOUTH);

	        JPanel infoPanel = new JPanel();
	        mainPanel.add(infoPanel, BorderLayout.SOUTH);
	        infoArea.setEditable(false);
	        infoArea.setVisible(true);
	        infoArea.setTabSize(2);
	        JScrollPane scroll = new JScrollPane(infoArea);
	        scroll.setPreferredSize(new Dimension(400, 250));
	        clearInfoArea();
	        infoPanel.add(scroll);

	        JPanel bottomPanel = new JPanel();
	        add(bottomPanel, BorderLayout.SOUTH);

	        img.setIcon(ImageManager.getImage(null));
	        img.setPreferredSize(new Dimension(ImageManager.getImageWidth(), ImageManager.getImageHeight()));
	        add(img, BorderLayout.EAST);

	        MenuBar mainMenu = new MenuBar();
	        Menu progremMenu = new Menu("Probleme");
	        Menu solutionMenu = new Menu("Solution");
	        mainMenu.add(progremMenu);
	        mainMenu.add(solutionMenu);

	        MenuItem loadPr = new MenuItem("Charger parametres par fichier");
	        MenuItem savePr = new MenuItem("Sauvegarder parametres");
	        progremMenu.add(loadPr);
	        progremMenu.add(savePr);

	        MenuItem saveSolText = new MenuItem("Sauvegarder solution (.txt)");
	        MenuItem saveSolIng = new MenuItem("Sauvegarder solution (.jpg)");
	        solutionMenu.add(saveSolText);
	        solutionMenu.add(saveSolIng);

	        MenuItem settings = new MenuItem("Limitation methode brute");
	        Menu m = new Menu("Parametres");
	        m.add(settings);
	        mainMenu.add(m);

	        setMenuBar(mainMenu);

	        pack();
	        setResizable(false);

	        geneticButton.addActionListener((e) -> geneticParamsPanel.setVisible(true));
	        approxButton.addActionListener((e) -> geneticParamsPanel.setVisible(false));
	        bruteButton.addActionListener((e) -> geneticParamsPanel.setVisible(false));

	        loadPr.addActionListener((e) -> {
	            JFileChooser chooser = new JFileChooser();
	            FileNameExtensionFilter filter = new FileNameExtensionFilter(
	                    "Text file", "txt");
	            chooser.setFileFilter(filter);
	            int returnVal = chooser.showOpenDialog(this);
	            if(returnVal == JFileChooser.APPROVE_OPTION) {
	                File f = chooser.getSelectedFile();
	                StringBuffer sb = new StringBuffer();
	                try (Scanner sc = new Scanner(f)) {
	                    while (sc.hasNextLine()) {
	                        sb.append(sc.nextLine());
	                        sb.append("\n");
	                    }
	                    inputArea.setText(sb.toString());
	                } catch (FileNotFoundException ex) {
	                    clearInfoArea();
	                    addInfo("Fichier " + f.getName() + " non trouvé");
	                }
	            }
	        });

	        savePr.addActionListener((e) -> {
	            JFileChooser chooser = new JFileChooser();
	            FileNameExtensionFilter filter = new FileNameExtensionFilter(
	                    "Text file", "txt");
	            chooser.setFileFilter(filter);
	            int returnVal = chooser.showSaveDialog(this);
	            if(returnVal == JFileChooser.APPROVE_OPTION) {
	                File f = chooser.getSelectedFile();
	                f = setExtension(f, "txt");
	                StringBuffer sb = new StringBuffer();
	                try (PrintWriter sc = new PrintWriter(f)) {
	                    sc.write(inputArea.getText());
	                } catch (FileNotFoundException ex) {
	                    clearInfoArea();
	                    addInfo("Fichier " + f.getName() + " non trouvé");
	                }
	            }
	        });

	        saveSolText.addActionListener((e) -> {
	            if (curSolution == null) {
	                clearInfoArea();
	                addInfo("Aucune solution trouvee");
	                return;
	            }
	            JFileChooser chooser = new JFileChooser();
	            FileNameExtensionFilter filter = new FileNameExtensionFilter(
	                    "Text file", "txt");
	            chooser.setFileFilter(filter);
	            int returnVal = chooser.showSaveDialog(this);
	            if(returnVal == JFileChooser.APPROVE_OPTION) {
	                File f = chooser.getSelectedFile();
	                f = setExtension(f, "txt");
	                StringBuffer sb = new StringBuffer();
	                try (PrintWriter sc = new PrintWriter(f)) {
	                    sc.write(curSolution.toString());
	                } catch (FileNotFoundException ex) {
	                    clearInfoArea();
	                    addInfo("Fichier " + f.getName() + " non trouvé");
	                }
	            }
	        });

	        saveSolIng.addActionListener((e) -> {
	            if (curSolution == null) {
	                clearInfoArea();
	                addInfo("Aucune solution trouvee");
	                return;
	            }
	            JFileChooser chooser = new JFileChooser();
	            FileNameExtensionFilter filter = new FileNameExtensionFilter(
	                    "Image JPG", "jpg");
	            chooser.setFileFilter(filter);
	            int returnVal = chooser.showSaveDialog(this);
	            if(returnVal == JFileChooser.APPROVE_OPTION) {
	                File f = chooser.getSelectedFile();
	                f = setExtension(f, "jpg");
	                StringBuffer sb = new StringBuffer();
	                try (FileOutputStream sc = new FileOutputStream(f)) {
	                    ImageIO.write(ImageManager.getBufferedImage(curSolution), "jpg", sc);
	                } catch (FileNotFoundException ex) {
	                    clearInfoArea();
	                    addInfo("Fichier " + f.getName() + " non trouvé");
	                } catch (IOException e1) {
	                    clearInfoArea();
	                    addInfo("Erreur durant la sauvegarde de l'image " + e1.getMessage());
	                }
	            }
	        });

	        settings.addActionListener((e) -> {
	            String limit =  JOptionPane.showInputDialog(
	                    this,
	                    "Via la méthode Brute, l'algorithme est limité par les operations:",
	                    String.valueOf(bruteLimitOnOperations));
	            if (limit != null) {
	                bruteLimitOnOperations = Integer.valueOf(limit);
	            }
	        });
	    }

	    protected File setExtension(File f, String ext) {
	        if (f.getName().endsWith("." + ext)) {
	            return f;
	        }
	        String newName = f.getAbsolutePath() + "." + ext;
	        return new File(newName);
	    }

	    protected void clearInfoArea() {
	        infoArea.setText("");
	    }

	    protected void addInfo(String s) {
	        String text = infoArea.getText();
	        if (!text.isEmpty()) {
	            text += "\n";
	        }
	        infoArea.setText(text + s);
	    }

	    public class ApplyButtonListener implements ActionListener {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            clearInfoArea();
	            Scanner sc = new Scanner(inputArea.getText());
	            Problem problem = Problem.read(sc);

	            addInfo(problem.toString());

	            Solver solver = null;
	            if (bruteButton.isSelected()) {
	                addInfo("Algorithme Brute:");
	                if (problem.getNumberOfMachines() * problem.getNumberOfJobs() > bruteLimitOnOperations) {
	                    addInfo("Ne peut pas continuer : \nle nombre d'opérations à réaliser dépasse le maximum = " + bruteLimitOnOperations);
	                    addInfo("La résolution prendrait trop de temps à être réalisée. \nVeuillez essayer avec l'algorithme Génétique.");
	                    return;
	                }
	                solver = new BruteOpenShop(problem);
	            } else if (approxButton.isSelected()) {
	                addInfo("Algorithme d'approximation");
	                solver = new ApproximateOpenShopCMax(problem);
	            } else if (geneticButton.isSelected()) {
	                addInfo("Algorithme génétique");
	                MakespanManager.MakespanManagerType makespanManager = null;
	                if (simple.isSelected()) {
	                    makespanManager = MakespanManager.MakespanManagerType.OPEN_SHOP_SIMPLE;
	                    addInfo("Ordonnancement simple");
	                } else if (modified.isSelected()) {
	                    makespanManager = MakespanManager.MakespanManagerType.OPEN_SHOP_MODIFIED;
	                    addInfo("Ordonnancement optimisée");
	                } else {
	                    addInfo("Erreur: veuillez selectionner un type d'ordonnancement");
	                    return;
	                }

	                int sizeOfPopulation = Integer.valueOf(sizeOfPopulationF.getText());
	                addInfo("Taille de la population: " + sizeOfPopulation);
	                double mutation = Double.valueOf(mutationF.getText());
	                addInfo(String.format("Probabilité de mutation: %.2f", mutation));
	                int iterrations = Integer.valueOf(iterationsF.getText());
	                addInfo("Iterations: " + iterrations);

	                solver = new GeneticOpenShopCMax(
	                        problem,
	                        makespanManager,
	                        ParentingManager.ParentingManagerType.CROSSOVER_WHEEL,
	                        CrossoverManager.CrossoverManagerType.RANDOM_CROSSOVER,
	                        MutationManager.MutationManagerType.SWAP_MUTATION,
	                        mutation,
	                        SelectionManager.SelectionManagerType.ELITE_SELECTION,
	                        sizeOfPopulation,
	                        iterrations,
	                        0);
	            } else {
	                System.out.println("Erreur !");
	            }

	            if (solver != null) {
	                curSolution = solver.generateSchedule();
	                img.setIcon(ImageManager.getImage(curSolution));
	                addInfo(curSolution.toString());
	                addInfo("Qualité:" + ((double) curSolution.getTime()) / problem.getLowerBorderOfSolution());
	            }
	        }


	    }

}
