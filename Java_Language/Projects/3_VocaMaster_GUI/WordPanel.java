package project2_ver1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * [단어 관리 화면]
 * 단어 목록을 JTable로 보여주고, 추가/수정/삭제/검색 기능을 제공합니다.
 * 북마크 필터링 기능도 포함되어 있습니다.
 */
public class WordPanel extends JPanel {
    VocManager manager;
    JTable table;
    DefaultTableModel model; // 테이블 데이터 모델
    JTextField tfSearch, tfEng, tfKor;

    Vector<Word> currentViewList; // 현재 화면에 표시 중인 리스트

    boolean isViewBookmarkOnly = false; // 북마크 필터 상태
    JButton btnFilterBook;

    public WordPanel(VocManager manager) {
        this.manager = manager;
        this.currentViewList = manager.voc; // 기본값은 전체 리스트

        setLayout(new BorderLayout(10, 10));

        add(createSearchPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createInputPanel(), BorderLayout.SOUTH);

        refreshTable(currentViewList); // 테이블 초기 표시
    }

    /**
     * [검색 패널 생성]
     * 상단의 검색어 입력창과 북마크 필터 버튼을 구성합니다.
     */
    private JPanel createSearchPanel() {
        JPanel p = new JPanel(new FlowLayout());
        tfSearch = new JTextField(20);
        JButton btnSearch = new JButton("검색");
        JButton btnShowAll = new JButton("초기화");

        btnFilterBook = new JButton("북마크만 보기");

        p.add(new JLabel("검색:"));
        p.add(tfSearch);
        p.add(btnSearch);
        p.add(btnShowAll);
        p.add(btnFilterBook);

        // 엔터키 및 버튼 클릭 이벤트
        tfSearch.addActionListener(e -> searchWord());
        btnSearch.addActionListener(e -> searchWord());

        // 초기화: 모든 필터를 해제하고 전체 목록 표시
        btnShowAll.addActionListener(e -> {
            tfSearch.setText("");
            isViewBookmarkOnly = false;
            btnFilterBook.setText("북마크만 보기");

            currentViewList = manager.voc;
            refreshTable(currentViewList);
        });

        btnFilterBook.addActionListener(e -> toggleFilter());

        return p;
    }

    /**
     * [북마크 필터 토글]
     * '북마크만 보기'와 '전체 보기' 상태를 전환합니다.
     */
    private void toggleFilter() {
        isViewBookmarkOnly = !isViewBookmarkOnly;

        if (isViewBookmarkOnly) {
            btnFilterBook.setText("전체 보기");
            // 북마크된 단어만 별도로 리스트 생성
            Vector<Word> bookmarkedList = new Vector<>();
            for (Word w : manager.voc) {
                if (w.isBookmarked()) {
                    bookmarkedList.add(w);
                }
            }
            refreshTable(bookmarkedList);
        } else {
            btnFilterBook.setText("북마크만 보기");
            refreshTable(currentViewList);
        }
    }

    /**
     * [테이블 화면 생성]
     * 단어 목록을 표시할 JTable을 생성하고, 클릭 시 선택된 단어를
     * 입력창으로 가져오는 기능을 연결합니다.
     */
    private JScrollPane createTablePanel() {
        String[] cols = {"영어", "뜻", "북마크(*)"};

        // 사용자가 셀 내용을 직접 수정하지 못하도록 설정
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(20);

        // 컬럼 너비 조정
        TableColumnModel cm = table.getColumnModel();
        cm.getColumn(0).setPreferredWidth(200);
        cm.getColumn(1).setPreferredWidth(200);
        cm.getColumn(2).setPreferredWidth(50);

        // 마우스 클릭 이벤트: 선택한 행의 데이터를 하단 입력창에 복사
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String eVal = (String)model.getValueAt(row, 0);
                    String kVal = (String)model.getValueAt(row, 1);
                    tfEng.setText(eVal.trim());
                    tfKor.setText(kVal.trim());
                }
            }
        });
        return new JScrollPane(table);
    }

    /**
     * [입력 및 버튼 패널 생성]
     * 하단의 단어 입력 필드와 기능 버튼들을 구성합니다.
     */
    private JPanel createInputPanel() {
        JPanel p = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel pIn = new JPanel(new FlowLayout());
        tfEng = new JTextField(10);
        tfKor = new JTextField(18);

        tfEng.setHorizontalAlignment(JTextField.CENTER);
        tfKor.setHorizontalAlignment(JTextField.CENTER);

        pIn.add(new JLabel("영어:")); pIn.add(tfEng);
        pIn.add(new JLabel("뜻:")); pIn.add(tfKor);

        JPanel pBtn = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("추가");
        JButton btnEdit = new JButton("수정");
        JButton btnDel = new JButton("삭제");
        JButton btnBook = new JButton("북마크 설정/해제");

        pBtn.add(btnAdd); pBtn.add(btnEdit); pBtn.add(btnDel); pBtn.add(btnBook);

        btnAdd.addActionListener(e -> addWord());
        btnEdit.addActionListener(e -> modifyWord());
        btnDel.addActionListener(e -> deleteWord());
        btnBook.addActionListener(e -> toggleBookmark());

        p.add(pIn);
        p.add(pBtn);
        return p;
    }

    private void addWord() {
        String e = tfEng.getText().trim();
        String k = tfKor.getText().trim();
        if(e.isEmpty() || k.isEmpty()) return;

        if(manager.addWord(e, k)) {
            // 추가 성공 시 입력창 초기화 및 리스트 갱신
            isViewBookmarkOnly = false;
            btnFilterBook.setText("북마크만 보기");
            tfSearch.setText("");
            currentViewList = manager.voc;
            refreshTable(currentViewList);
            clearInput();
        } else {
            JOptionPane.showMessageDialog(this, "이미 존재하는 단어입니다.");
        }
    }

    private void modifyWord() {
        String e = tfEng.getText().trim();
        String k = tfKor.getText().trim();

        if(e.isEmpty()) {
            JOptionPane.showMessageDialog(this, "수정할 단어를 입력하거나 선택해주세요.");
            return;
        }

        Word w = manager.findWord(e);
        if(w != null) {
            w.setKor(k); // 뜻 변경
            manager.saveVoc();

            // 현재 보고 있는 화면 상태 유지하며 갱신
            if(isViewBookmarkOnly) toggleFilter();
            else refreshTable(currentViewList);

            clearInput();
            JOptionPane.showMessageDialog(this, "수정되었습니다.");
        } else {
            JOptionPane.showMessageDialog(this, "수정할 단어가 존재하지 않습니다.");
        }
    }

    private void deleteWord() {
        String e = tfEng.getText().trim();

        if(e.isEmpty()) {
            JOptionPane.showMessageDialog(this, "삭제할 단어를 입력하거나 선택해주세요.");
            return;
        }

        Word w = manager.findWord(e);
        if(w != null) {
            manager.voc.remove(w);
            manager.saveVoc();

            // 현재 뷰 리스트에서도 제거
            if(currentViewList != manager.voc) {
                currentViewList.remove(w);
            }

            // 화면 갱신
            if(isViewBookmarkOnly) {
                isViewBookmarkOnly = !isViewBookmarkOnly;
                toggleFilter();
            } else {
                refreshTable(currentViewList);
            }

            clearInput();
            JOptionPane.showMessageDialog(this, "삭제되었습니다.");
        } else {
            JOptionPane.showMessageDialog(this, "삭제할 단어가 존재하지 않습니다.");
        }
    }

    private void toggleBookmark() {
        String e = tfEng.getText().trim();

        if(e.isEmpty()) {
            JOptionPane.showMessageDialog(this, "북마크할 단어를 입력하거나 선택해주세요.");
            return;
        }

        Word w = manager.findWord(e);
        if(w != null) {
            w.setBookmarked(!w.isBookmarked()); // 상태 반전
            manager.saveVoc();

            if(isViewBookmarkOnly) {
                toggleFilter();
            } else {
                refreshTable(currentViewList);
            }
        } else {
            JOptionPane.showMessageDialog(this, "북마크할 단어가 존재하지 않습니다.");
        }
    }

    /**
     * [검색 로직]
     * 입력된 키워드가 포함된 단어를 찾아 리스트를 갱신합니다.
     */
    private void searchWord() {
        String keyword = tfSearch.getText().trim();
        if(keyword.isEmpty()) {
            currentViewList = manager.voc;
            refreshTable(currentViewList);
            return;
        }

        Vector<Word> result = new Vector<>();
        for(Word w : manager.voc) {
            // 대소문자 무시하고 포함 여부 확인 (contains)
            if(w.getEng().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(w);
            }
        }

        currentViewList = result;

        // 검색 시 필터는 해제
        isViewBookmarkOnly = false;
        btnFilterBook.setText("북마크만 보기");
        refreshTable(currentViewList);

        if(result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다.");
        }
    }

    /**
     * [테이블 갱신]
     * 데이터 리스트를 받아 테이블 모델을 초기화하고 다시 채웁니다.
     */
    private void refreshTable(Vector<Word> data) {
        model.setRowCount(0); // 기존 데이터 삭제
        for(Word w : data) {
            String engSpace = "    " + w.getEng();
            String korSpace = "    " + w.getKor();
            String bookmark = w.isBookmarked() ? "  ★" : "";

            model.addRow(new Object[]{engSpace, korSpace, bookmark});
        }
    }

    private void clearInput() {
        tfEng.setText(""); tfKor.setText(""); tfEng.requestFocus();
    }
}