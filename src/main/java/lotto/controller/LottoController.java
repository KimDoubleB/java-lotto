package lotto.controller;

import lotto.domain.LottoService;
import lotto.domain.LottoTicket;
import lotto.domain.LottoNumber;
import lotto.domain.LotteryNumber;
import lotto.domain.Rank;
import lotto.domain.Money;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LottoController {
    private final LottoService lottoService;
    private Map<Rank, Integer> lottoRankingStatus;

    public LottoController(){
        lottoService = new LottoService();
    }

    public LottoController(LottoService lottoService){
        this.lottoService = lottoService;
    }

    public void buyLottoProcess() {
        Money price = new Money(InputView.inputPrice());
        int numberOfLotto = price.getAmount() / LottoTicket.PRICE;

        lottoService.buyLottoTickets(numberOfLotto);
        OutputView.printNumberOfLotto(numberOfLotto);
        List<LottoTicket> lottoTickets = lottoService.getLottoTickets();
        for (LottoTicket lottoTicket : lottoTickets) {
            OutputView.printLottoPickedNumber(lottoTicket);
        }
    }

    public void pickLotteryNumber() {
        List<LottoNumber> winningNumbers = InputView.inputWinningNumbers()
                .stream()
                .map(LottoNumber::new)
                .collect(Collectors.toList());
        LottoNumber bonusNumber = new LottoNumber(InputView.inputBonusNumber());

        LotteryNumber lotteryNumber = new LotteryNumber(winningNumbers, bonusNumber);
        lottoService.recordLotteryNumber(lotteryNumber);
    }

    public void showLottoResult() {
        lottoRankingStatus = Rank.getInitRankingDict();
        int profit = lottoService.calculateResult(lottoRankingStatus);
        double interestRate = lottoService.getInterestRate(profit);
        OutputView.printLottoServiceResult(lottoRankingStatus, interestRate);
    }

    public Map<Rank, Integer> getLottoRankingStatus() {
        return lottoRankingStatus;
    }
}
